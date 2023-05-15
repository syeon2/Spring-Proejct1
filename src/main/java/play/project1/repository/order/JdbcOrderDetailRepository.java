package play.project1.repository.order;

import static play.project1.repository.order.sql.OrderSQL.OrderDetail.*;

import java.sql.PreparedStatement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import play.project1.domain.order.OrderDetail;

@Repository
@RequiredArgsConstructor
public class JdbcOrderDetailRepository implements OrderDetailRepository {

	private final JdbcTemplate template;

	@Override
	public void save(OrderDetail orderDetail) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update((con) -> {
			PreparedStatement ps = con.prepareStatement(INSERT, new String[] {"id"});
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
		template.update(DELETE, orderId);
	}
}
