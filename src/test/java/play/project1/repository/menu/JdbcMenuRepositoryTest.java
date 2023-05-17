package play.project1.repository.menu;

import static org.assertj.core.api.Assertions.*;
import static play.project1.domain.menu.Category.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import play.project1.domain.menu.Menu;
import play.project1.dto.menu.MenuSaveDTO;
import play.project1.dto.menu.MenuUpdateDTO;

@SpringBootTest
@Sql(scripts = {"classpath:schema_mysql.sql"})
class JdbcMenuRepositoryTest {

	@Autowired
	private MenuRepository repository;

	@Test
	@DisplayName("메뉴 저장, 수정, 조회")
	void saveAndFind() {
		// given
		String MENU_A = "menuA";
		MenuSaveDTO menuA = new MenuSaveDTO(MENU_A, COFFEE.getMenuCode());

		// // when
		repository.save(menuA);
		List<Menu> findMenu = repository.findByName(MENU_A);
		Menu menu = findMenu.get(0);
		assertThat(menu.getName()).isEqualTo(MENU_A);

		String updateMenuName = "menuB";
		repository.update(menu.getId(), new MenuUpdateDTO(updateMenuName, BigDecimal.valueOf(20000), COFFEE.getMenuCode(), 0L));
		Menu updateMenu = repository.findByName(updateMenuName).get(0);
		assertThat(updateMenu.getPrice().intValue()).isEqualTo(20000);

		// then
		assertThat(updateMenu.getName()).isEqualTo(updateMenuName);
	}
}