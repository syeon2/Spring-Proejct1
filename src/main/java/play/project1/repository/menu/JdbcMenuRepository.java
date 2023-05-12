package play.project1.repository.menu;

import static play.project1.domain.menu.Menu.*;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import play.project1.domain.menu.Menu;
import play.project1.dto.menu.MenuSaveDTO;
import play.project1.dto.menu.MenuUpdateDTO;

@Repository
public class JdbcMenuRepository implements MenuRepository {

	private final JdbcTemplate template;

	public JdbcMenuRepository(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public Menu save(MenuSaveDTO menuSaveDTO) {
		String sql = "insert into menu(NAME, MENU_CODE) values (?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update((connection) -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] {ID});
			ps.setString(1, menuSaveDTO.getName());
			ps.setInt(2, menuSaveDTO.getMenuCode());

			return ps;
		}, keyHolder);

		long id = keyHolder.getKey().longValue();

		return Menu.createNewMenu(id, menuSaveDTO.getName(), menuSaveDTO.getMenuCode());
	}

	@Override
	public List<Menu> findByName(String name) {
		String sql = "select * from menu where name like '%" + name + "%'";

		return template.query(sql, menuRowMapper());
	}

	@Override
	public Menu findById(Long menuId) {
		String sql = "select * from menu where id = ?";

		return template.queryForObject(sql, menuRowMapper(), menuId);
	}

	@Override
	public void update(Long menuId, MenuUpdateDTO menuDTO) {
		String sql = "update menu set name = ?, price = ?, menu_code = ?, total_order = ? where id = ?";

		template.update(sql, menuDTO.getName(), menuDTO.getPrice(), menuDTO.getMenuCode(), menuDTO.getTotalOrder(), menuId);
	}

	@Override
	public void delete(Long menuId) {
		String sql = "delete from menu where id = ?";

		template.update(sql, menuId);
	}

	private RowMapper<Menu> menuRowMapper() {
		return ((rs, rowNum) -> {
			Long id = rs.getLong(ID);
			String name = rs.getString(NAME);
			BigDecimal price = rs.getBigDecimal(PRICE);
			Integer menuCode = rs.getInt(MENU_CODE);
			Long totalOrder = rs.getLong(TOTAL_ORDER);

			return new Menu(id, name, menuCode, price, totalOrder);
		});
	}
}
