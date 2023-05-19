package play.project1.repository.order;

import static play.project1.repository.order.sql.OrderSQL.OrderDetail.*;

import java.sql.PreparedStatement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import play.project1.dto.order.OrderDetailSaveDTO;
import play.project1.util.connection.DBKeyGenerator;

@Repository
@RequiredArgsConstructor
public class JdbcOrderDetailRepository implements OrderDetailRepository {

	private final JdbcTemplate template;

	@Override
	public void save(OrderDetailSaveDTO orderDetailSaveDTO) {
		KeyHolder keyHolder = DBKeyGenerator.getInstance();
		template.update((con) -> {
			PreparedStatement ps = con.prepareStatement(INSERT, new String[] {"id"});
			ps.setLong(1, orderDetailSaveDTO.getOrderId());
			ps.setString(2, orderDetailSaveDTO.getMemberId());
			ps.setLong(3, orderDetailSaveDTO.getMenuId());
			ps.setLong(4, orderDetailSaveDTO.getCount());
			ps.setBigDecimal(5, orderDetailSaveDTO.getPrice());

			return ps;
		}, keyHolder);
	}

	@Override
	public void delete(Long orderId) {
		template.update(DELETE, orderId);
	}
}
