package play.project1.menu;

import static org.assertj.core.api.Assertions.*;
import static play.project1.domain.menu.Category.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import play.project1.domain.menu.Menu;
import play.project1.repository.menu.MenuRepository;
import play.project1.service.menu.dto.MenuDTO;

@Transactional
@SpringBootTest
class MySqlMenuRepositoryTest {

	private long sequence = 1L;

	@Autowired
	private MenuRepository repository;

	@Test
	@DisplayName("메뉴 저장, 수정, 조회")
	void saveAndFind() {
		// given
		String MENU_A = "menuA";
		Menu menuA = new Menu(sequence++, MENU_A, COFFEE.getMenuCode(), BigDecimal.valueOf(10000), 0L);

		// // when
		repository.save(menuA);
		List<Menu> findMenu = repository.findByName(MENU_A);
		Menu menu = findMenu.get(0);
		assertThat(menu.getName()).isEqualTo(MENU_A);

		String updateMenuName = "menuB";
		repository.update(menu.getId(), new MenuDTO(updateMenuName, BigDecimal.valueOf(20000), COFFEE.getMenuCode(), 0L));
		Menu updateMenu = repository.findByName(updateMenuName).get(0);
		assertThat(updateMenu.getPrice().intValue()).isEqualTo(20000);

		// then
		assertThat(updateMenu.getName()).isEqualTo(updateMenuName);
	}
}