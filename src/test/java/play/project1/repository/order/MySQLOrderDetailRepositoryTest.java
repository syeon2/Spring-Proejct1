package play.project1.repository.order;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import play.project1.domain.order.OrderDetail;
import play.project1.dto.order.OrderDetailSaveDTO;

@SpringBootTest
@Sql(scripts = {"classpath:schema_mysql.sql"})
class MySQLOrderDetailRepositoryTest {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Test
	void saveAndDelete() {
		// save
		Long orderId = 1L;
		OrderDetailSaveDTO itemA = new OrderDetailSaveDTO(orderId, "4568ksy@naver.com", 1L, 1, BigDecimal.valueOf(4000));
		OrderDetailSaveDTO itemB = new OrderDetailSaveDTO(orderId, "4568ksy@naver.com", 2L, 1, BigDecimal.valueOf(5000));
		OrderDetailSaveDTO itemC = new OrderDetailSaveDTO(orderId, "4568ksy@naver.com", 3L, 1, BigDecimal.valueOf(6000));

		orderDetailRepository.save(itemA);
		orderDetailRepository.save(itemB);
		orderDetailRepository.save(itemC);

		// delete
		orderDetailRepository.delete(orderId);
	}
}