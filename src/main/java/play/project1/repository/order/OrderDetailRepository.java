package play.project1.repository.order;

import java.util.List;

import play.project1.domain.order.OrderDetail;

public interface OrderDetailRepository {

	void save(OrderDetail orderDetails);
	void delete(Long orderId);
}
