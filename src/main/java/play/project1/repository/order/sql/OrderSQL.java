package play.project1.repository.order.sql;

public abstract class OrderSQL {

	public static class OrderList {

		public static final String INSERT = "insert into order_list(member_id, menu_count, total_price) values (?, ?, ?)";
		public static final String DELETE = "delete from order_list where id = ?";
	}

	public static class OrderDetail {

		public static final String INSERT = "insert into order_detail(order_list_id, member_id, menu_id, menu_count, price) values (?, ?, ?, ?, ?)";
		public static final String DELETE = "delete from order_detail where order_list_id = ?";
	}
}
