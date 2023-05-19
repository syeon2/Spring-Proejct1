package play.project1.repository.order;

import play.project1.domain.order.OrderList;
import play.project1.dto.order.OrderListSaveDTO;

public interface OrderListRepository {

	OrderList save(OrderListSaveDTO orderListSaveDTO);
	void delete(Long orderId);
}
