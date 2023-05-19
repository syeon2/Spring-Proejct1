package play.project1.service.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import play.project1.domain.member.Member;
import play.project1.domain.menu.Menu;
import play.project1.domain.order.OrderDetail;
import play.project1.domain.order.OrderList;
import play.project1.dto.order.OrderDetailRequestDTO;
import play.project1.dto.order.OrderRequestDTO;
import play.project1.repository.order.OrderDetailRepository;
import play.project1.repository.order.OrderListRepository;
import play.project1.service.member.MemberService;
import play.project1.service.menu.MenuService;
import play.project1.util.executor.AsyncThreadPool;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderListRepository orderListRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final MemberService memberService;
	private final MenuService menuService;

	private final ExecutorService executorService;

	@Transactional
	public OrderList createOrder(Map<Menu, Integer> orderMenu, OrderRequestDTO orderRequestDTO) {
		// 메뉴 총 주문내역 카운트 + 1 및 메뉴 총 가격 계산
		Integer menuTotalCount = 0;
		BigDecimal totalPrice = BigDecimal.ZERO;

		for (Menu menu : orderMenu.keySet()) {
			Integer orderCount = orderMenu.get(menu);
			BigDecimal menuPrice = menu.getPrice().multiply(BigDecimal.valueOf(orderCount));

			totalPrice = totalPrice.add(menuPrice);
			menuTotalCount += orderMenu.get(menu);
		}

		// 포인트 차감
		memberService.usePoints(totalPrice, orderRequestDTO.getMemberId());

		// 주문 리스트 생성
		OrderList orderList = createOrderList(menuTotalCount, totalPrice, orderRequestDTO.getMemberId());

		// 주문 디테일 생성
		createOrderDetails(orderMenu, orderRequestDTO.getMemberId(), orderList.getId());

		return orderList;
	}

	public Map<Menu, Integer> getOrderMenusAndCount(List<OrderDetailRequestDTO> orderDetailRequestDTOList) {
		Map<Menu, Integer> orderMenu = new HashMap<>();

		for (OrderDetailRequestDTO orderDetail : orderDetailRequestDTOList) {
			Menu menu = menuService.findMenu(orderDetail.getMenuId());
			orderMenu.put(menu, orderDetail.getCount());
		}

		return orderMenu;
	}

	public void updateTotalMenuCount(Map<Menu, Integer> orderMenu) {
		for (Menu menu : orderMenu.keySet()) {
			menuService.addTotalCount(menu, orderMenu.get(menu));
			menuService.countMenuToRedis(menu, menu.getTotalOrder().intValue() + orderMenu.get(menu));
		}
	}

	private OrderList createOrderList(Integer menuTotalCount, BigDecimal totalPrice, String memberId) {
		return orderListRepository.save(new OrderList(null, memberId, menuTotalCount, totalPrice));
	}

	private void createOrderDetails(Map<Menu, Integer> orderMenu, String memberId, Long orderId) {
		for (Menu menu : orderMenu.keySet()) {
			executorService.submit(() -> {
				Integer menuCount = orderMenu.get(menu);
				BigDecimal sameMenuTotalPrice = menu.getPrice().multiply(BigDecimal.valueOf(orderMenu.get(menu)));

				orderDetailRepository.save(
					new OrderDetail(null, orderId, memberId, menu.getId(), menuCount, sameMenuTotalPrice)
				);
			});
		}
	}
}
