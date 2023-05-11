package play.project1.service.order;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import play.project1.domain.member.Member;
import play.project1.repository.member.MemberRepository;
import play.project1.service.member.MemberService;
import play.project1.service.order.dto.OrderDTO;
import play.project1.service.order.dto.OrderDetailDTO;

@Transactional
@SpringBootTest
class OrderServiceTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberService memberService;

	@Test
	@DisplayName("회원 3만원 충전 - 주문 성공")
	void createOrder() {
		// given
		String memberId = "gsy4568@gmail.com";
		memberRepository.save(new Member(memberId, "testing", BigDecimal.ZERO));
		memberService.chargePoint(memberId, BigDecimal.valueOf(30000));

		// when
		OrderDTO orderDTO = new OrderDTO(memberId, List.of(new OrderDetailDTO(1L, 2)));

		// then
		orderService.createOrder(orderDTO);
	}

	@Test
	@DisplayName("포인트 부족 - 주문 실패")
	void createOrder_X() {
		// given
		String memberId = "gsy4568@gmail.com";
		memberRepository.save(new Member(memberId, "testing", BigDecimal.ZERO));

		// when
		OrderDTO orderDTO = new OrderDTO(memberId, List.of(new OrderDetailDTO(1L, 2)));

		// then
		assertThatThrownBy(() -> orderService.createOrder(orderDTO))
			.isInstanceOf(IllegalStateException.class);
	}
}