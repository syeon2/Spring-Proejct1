package play.project1.repository.order;

import play.project1.dto.order.OrderDetailSaveDTO;

public interface OrderDetailRepository {

	void save(OrderDetailSaveDTO orderDetails);
	void delete(Long orderId);
}
