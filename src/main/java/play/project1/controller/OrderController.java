package play.project1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import play.project1.service.order.OrderService;
import play.project1.service.order.dto.OrderDTO;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/order")
	public void order(@RequestBody OrderDTO orderDTO) {
		try {
			orderService.createOrder(orderDTO);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
}