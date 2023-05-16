package play.project1.repository.order;

import static play.project1.repository.order.sql.OrderSQL.OrderList.*;

import java.sql.PreparedStatement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import play.project1.domain.order.OrderList;
import play.project1.util.connection.DBKeyGenerator;

@Repository
@RequiredArgsConstructor
public class JdbcOrderListRepository implements OrderListRepository {

	private final JdbcTemplate template;

	@Override
	public OrderList save(OrderList orderList) {
		KeyHolder keyHolder = DBKeyGenerator.getInstance();

		template.update((connection) -> {
			PreparedStatement ps = connection.prepareStatement(INSERT, new String[] {"id"});
			ps.setString(1, orderList.getMemberId());
			ps.setInt(2, orderList.getMenuCount());
			ps.setBigDecimal(3, orderList.getTotalPrice());

			return ps;
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return new OrderList(id, orderList.getMemberId(), orderList.getMenuCount(), orderList.getTotalPrice());
	}

	@Override
	public void delete(Long orderId) {
		template.update(DELETE, orderId);
	}
}
