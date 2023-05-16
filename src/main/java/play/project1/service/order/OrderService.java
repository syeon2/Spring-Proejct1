package play.project1.service.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import play.project1.domain.menu.Menu;
import play.project1.domain.order.OrderDetail;
import play.project1.domain.order.OrderList;
import play.project1.dto.menu.MenuUpdateDTO;
import play.project1.dto.order.OrderDetailRequestDTO;
import play.project1.dto.order.OrderRequestDTO;
import play.project1.repository.menu.MenuRepository;
import play.project1.repository.order.OrderDetailRepository;
import play.project1.repository.order.OrderListRepository;
import play.project1.service.member.MemberService;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderListRepository orderListRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final MemberService memberService;
	private final MenuRepository menuRepository;
	private final RedisTemplate<String, Object> redisTemplate;

	@Transactional
	public OrderList createOrder(OrderRequestDTO orderRequestDTO) {

		// 주문한 커피들 모두 조회 -> Map 에 저장
		Map<Menu, Integer> orderMenu = new HashMap<>();
		getOrderMenus(orderRequestDTO.getOrderMenuList(), orderMenu);

		// 메뉴 총 주문내역 카운트 + 1 및 메뉴 총 가격 계산
		Integer menuTotalCount = 0;
		BigDecimal totalPrice = BigDecimal.ZERO;

		for (Menu menu : orderMenu.keySet()) {
			Integer orderCount = orderMenu.get(menu);
			BigDecimal menuPrice = menu.getPrice().multiply(BigDecimal.valueOf(orderCount));

			updateMenuOrderCount(menu, orderCount, menuPrice);
			totalPrice = getMenusPrice(totalPrice, orderCount, menuPrice);
			menuTotalCount += orderMenu.get(menu);
		}

		// 포인트 차감
		memberService.usePoints(totalPrice, orderRequestDTO.getMemberId());

		// 주문 리스트 생성
		OrderList orderList = createOrderList(menuTotalCount, totalPrice, orderRequestDTO.getMemberId());

		// 주문 디테일 생성
		createOrderDetails(orderMenu, orderRequestDTO.getMemberId(), orderList.getId());

		// redis popular menu count
		for (Menu menu : orderMenu.keySet()) {
			Integer menuCount = orderMenu.get(menu);
			countMenuToRedis(menu, menu.getTotalOrder().intValue() + menuCount);
		}

		return orderList;
	}

	private void countMenuToRedis(Menu menu, Integer orderCount) {
		redisTemplate.opsForZSet().incrementScore("menu", String.valueOf(menu.getId()), orderCount);
	}

	private OrderList createOrderList(Integer menuTotalCount, BigDecimal totalPrice, String memberId) {
		return orderListRepository.save(new OrderList(null, memberId, menuTotalCount, totalPrice));
	}

	private void createOrderDetails(Map<Menu, Integer> orderMenu, String memberId, Long orderId) {
		for (Menu menu : orderMenu.keySet()) {
			Integer menuCount = orderMenu.get(menu);
			BigDecimal sameMenuTotalPrice = menu.getPrice().multiply(BigDecimal.valueOf(orderMenu.get(menu)));

			orderDetailRepository.save(
				new OrderDetail(null, orderId, memberId, menu.getId(), menuCount, sameMenuTotalPrice)
			);
		}
	}

	private static BigDecimal getMenusPrice(BigDecimal totalPrice, Integer orderCount, BigDecimal menuPrice) {
		return totalPrice.add(menuPrice);
	}

	private void updateMenuOrderCount(Menu menu, Integer orderCount, BigDecimal menuPrice) {
		menuRepository.update(menu.getId(),
			new MenuUpdateDTO(menu.getName(), menu.getPrice(), menu.getMenuCode(), menu.getTotalOrder() + orderCount));
	}

	private void getOrderMenus(List<OrderDetailRequestDTO> orderMenuList, Map<Menu, Integer> orderMenu) {
		for (OrderDetailRequestDTO orderDetail : orderMenuList) {
			Menu menu = menuRepository.findById(orderDetail.getMenuId());
			orderMenu.put(menu, orderDetail.getCount());
		}
	}
}
