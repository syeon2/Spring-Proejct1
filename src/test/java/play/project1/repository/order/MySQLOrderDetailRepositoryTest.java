package play.project1.repository.order;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import play.project1.domain.order.OrderDetail;

// @Transactional
@SpringBootTest
class MySQLOrderDetailRepositoryTest {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Test
	void saveAndDelete() {
		// save
		Long orderId = 1L;
		OrderDetail itemA = new OrderDetail(1L, orderId, "4568ksy@naver.com", 1L, 1, BigDecimal.valueOf(4000));
		OrderDetail itemB = new OrderDetail(2L, orderId, "4568ksy@naver.com", 2L, 1, BigDecimal.valueOf(5000));
		OrderDetail itemC = new OrderDetail(3L, orderId, "4568ksy@naver.com", 3L, 1, BigDecimal.valueOf(6000));

		orderDetailRepository.save(itemA);
		orderDetailRepository.save(itemB);
		orderDetailRepository.save(itemC);

		// delete
		orderDetailRepository.delete(orderId);
	}
}