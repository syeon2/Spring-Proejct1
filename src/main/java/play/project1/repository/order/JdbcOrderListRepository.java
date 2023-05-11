package play.project1.repository.order;

import java.sql.PreparedStatement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import play.project1.domain.order.OrderList;

@Repository
@RequiredArgsConstructor
public class JdbcOrderListRepository implements OrderListRepository {

	private final JdbcTemplate template;

	@Override
	public Long save(OrderList orderList) {
		String sql = "insert into order_list(member_id, menu_count, total_price) values (?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update((connection) -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
			ps.setString(1, orderList.getMemberId());
			ps.setInt(2, orderList.getMenuCount());
			ps.setBigDecimal(3, orderList.getTotalPrice());

			return ps;
		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	@Override
	public void delete(Long orderId) {
		String sql = "delete from order_list where id = ?";

		template.update(sql, orderId);
	}
}
