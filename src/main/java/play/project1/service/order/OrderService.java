package play.project1.service.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.RequiredArgsConstructor;
import play.project1.domain.menu.Menu;
import play.project1.domain.order.OrderList;
import play.project1.dto.order.OrderDetailRequestDTO;
import play.project1.dto.order.OrderDetailSaveDTO;
import play.project1.dto.order.OrderListSaveDTO;
import play.project1.dto.order.OrderRequestDTO;
import play.project1.repository.order.OrderDetailRepository;
import play.project1.repository.order.OrderListRepository;
import play.project1.service.member.MemberService;
import play.project1.service.menu.MenuService;

@Service
@EnableAsync
@RequiredArgsConstructor
public class OrderService {

	private final OrderListRepository orderListRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final MemberService memberService;
	private final MenuService menuService;

	private final TransactionTemplate txTemplate;

	public OrderList createOrder(Map<Menu, Integer> orderMenu, OrderRequestDTO orderRequestDTO) {
		return txTemplate.execute((status) -> {
			Integer menuTotalCount = 0;
			BigDecimal totalPrice = BigDecimal.ZERO;

			for (Menu menu : orderMenu.keySet()) {
				Integer orderCount = orderMenu.get(menu);
				BigDecimal menuPrice = menu.getPrice().multiply(BigDecimal.valueOf(orderCount));

				totalPrice = totalPrice.add(menuPrice);
				menuTotalCount += orderMenu.get(menu);
			}

			for (Menu menu : orderMenu.keySet()) {
				try {
					menuService.addTotalCount(menu, orderMenu.get(menu));
				} catch (Throwable e) {
					status.setRollbackOnly();
				}
				menuService.countMenuToRedis(menu, menu.getTotalOrder().intValue() + orderMenu.get(menu));
			}
			// 포인트 차감
			memberService.usePoints(totalPrice, orderRequestDTO.getMemberId());

			// 주문 리스트 생성
			OrderList orderList = createOrderList(menuTotalCount, totalPrice, orderRequestDTO.getMemberId());

			// 주문 디테일 생성
			try {
				createOrderDetails(orderMenu, orderRequestDTO.getMemberId(), orderList.getId());
			} catch (Throwable e) {
				status.setRollbackOnly();
			}

			return orderList;
		});
	}

	public Map<Menu, Integer> getOrderMenusAndCount(List<OrderDetailRequestDTO> orderDetailRequestDTOList) {
		Map<Menu, Integer> orderMenu = new HashMap<>();

		for (OrderDetailRequestDTO orderDetail : orderDetailRequestDTOList) {
			Menu menu = menuService.findMenu(orderDetail.getMenuId());
			orderMenu.put(menu, orderDetail.getCount());
		}

		return orderMenu;
	}

	private OrderList createOrderList(Integer menuTotalCount, BigDecimal totalPrice, String memberId) {
		return orderListRepository.save(new OrderListSaveDTO(memberId, menuTotalCount, totalPrice));
	}

	@Async
	public void createOrderDetails(Map<Menu, Integer> orderMenu, String memberId, Long orderId) {
		for (Menu menu : orderMenu.keySet()) {
			Integer menuCount = orderMenu.get(menu);
			BigDecimal sameMenuTotalPrice = menu.getPrice().multiply(BigDecimal.valueOf(orderMenu.get(menu)));

			orderDetailRepository.save(new OrderDetailSaveDTO(orderId, memberId, menu.getId(), menuCount, sameMenuTotalPrice));
		}
	}
}
