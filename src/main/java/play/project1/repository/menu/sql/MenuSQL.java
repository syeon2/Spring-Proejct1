package play.project1.repository.menu.sql;

public abstract class MenuSQL {

	public static final String INSERT = "insert into menu(NAME, MENU_CODE) values (?, ?)";
	public static final String FIND_BY_ID = "select * from menu where id = ? for update";
	public static final String UPDATE = "update menu set name = ?, price = ?, menu_code = ?, total_order = ? where id = ?";
	public static final String DELETE = "delete from menu where id = ?";

	public static String FIND_BY_NAME(String name) {
		return "select * from menu where name like '%" + name + "%'";
	}
}
