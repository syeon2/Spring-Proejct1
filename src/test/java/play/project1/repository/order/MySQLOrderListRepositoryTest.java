package play.project1.repository.order;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import play.project1.domain.order.OrderList;
import play.project1.dto.order.OrderListSaveDTO;

@SpringBootTest
@Sql(scripts = {"classpath:schema_mysql.sql"})
class MySQLOrderListRepositoryTest {

	@Autowired
	private OrderListRepository orderListRepository;

	@Test
	void saveAndDelete() {
		// save
		String memberId = "4568ksy@naver.com";
		OrderListSaveDTO order = new OrderListSaveDTO(memberId, 1, BigDecimal.valueOf(4000));
		OrderList orderList = orderListRepository.save(order);

		// delete
		orderListRepository.delete(1L);
	}
}