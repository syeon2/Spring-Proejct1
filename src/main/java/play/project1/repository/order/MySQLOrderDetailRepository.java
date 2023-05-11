package play.project1.repository.order;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import play.project1.domain.order.OrderDetail;

@Repository
@RequiredArgsConstructor
public class MySQLOrderDetailRepository implements OrderDetailRepository {

	private final JdbcTemplate template;

	@Override
	public void save(OrderDetail orderDetail) {
		String sql = "insert into order_detail(order_list_id, member_id, menu_id, menu_count, price) values (?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update((con) -> {
			PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
			ps.setLong(1, orderDetail.getOrderId());
			ps.setString(2, orderDetail.getMemberId());
			ps.setLong(3, orderDetail.getMenuId());
			ps.setLong(4, orderDetail.getCount());
			ps.setBigDecimal(5, orderDetail.getPrice());

			return ps;
		}, keyHolder);
	}

	@Override
	public void delete(Long orderId) {
		String sql = "delete from order_detail where order_list_id = ?";

		template.update(sql, orderId);
	}
}
