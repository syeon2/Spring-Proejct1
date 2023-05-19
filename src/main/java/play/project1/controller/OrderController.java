package play.project1.controller;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import play.project1.domain.menu.Menu;
import play.project1.domain.order.OrderList;
import play.project1.dto.order.OrderRequestDTO;
import play.project1.service.order.OrderService;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public OrderList order(@Validated @RequestBody OrderRequestDTO orderRequestDTO) {
		Map<Menu, Integer> orderMenusAndCount = orderService.getOrderMenusAndCount(orderRequestDTO.getOrderMenuList());

		orderService.updateTotalMenuCount(orderMenusAndCount);

		return orderService.createOrder(orderMenusAndCount, orderRequestDTO);
	}
}