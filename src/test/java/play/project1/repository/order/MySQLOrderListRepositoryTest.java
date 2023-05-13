package play.project1.repository.order;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import play.project1.domain.order.OrderList;

// @Transactional
@SpringBootTest
class MySQLOrderListRepositoryTest {

	@Autowired
	private OrderListRepository orderListRepository;

	@Test
	void saveAndDelete() {
		// save
		String memberId = "4568ksy@naver.com";
		OrderList order = new OrderList(1L, memberId, 1, BigDecimal.valueOf(4000));
		Long orderId = orderListRepository.save(order);

		// delete
		orderListRepository.delete(orderId);
	}
}