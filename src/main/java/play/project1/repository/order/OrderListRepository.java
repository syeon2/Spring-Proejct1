package play.project1.repository.order;

import play.project1.domain.order.OrderList;

public interface OrderListRepository {

	OrderList save(OrderList orderList);
	void delete(Long orderId);
}
