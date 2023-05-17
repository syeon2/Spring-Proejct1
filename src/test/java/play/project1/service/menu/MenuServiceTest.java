package play.project1.service.menu;

import static org.assertj.core.api.Assertions.*;
import static play.project1.domain.menu.Category.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import play.project1.domain.menu.Menu;
import play.project1.dto.menu.MenuSaveDTO;
import play.project1.repository.menu.MenuRepository;

@SpringBootTest
@Sql(scripts = {"classpath:schema_mysql.sql"})
class MenuServiceTest {

    private final String MENU = "menu";
    private final String MENU_A = "menuA";
    private final String MENU_B = "menuB";
    private final String MENU_C = "menuC";
    private final String MENU_EX = "ex";

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @Test
    @DisplayName("메뉴 이름으로 테이블 조회")
    void findMenus() {
        // given
        menuRepository.save(new MenuSaveDTO(MENU_A, COFFEE.getMenuCode()));
        menuRepository.save(new MenuSaveDTO(MENU_B, COFFEE.getMenuCode()));
        menuRepository.save(new MenuSaveDTO(MENU_C, COFFEE.getMenuCode()));

        // when
        List<Menu> findMenusLatte = menuService.findMenus(MENU);

        // then
        assertThat(findMenusLatte.size()).isEqualTo(3);
        assertThat(findMenusLatte).containsAll(findMenusLatte);
    }

    @Test
    @DisplayName("메뉴가 없을 시 빈 List 반환")
    void getEmptyListNotFoundMenu() {
        // when
        List<Menu> findMenus = menuService.findMenus(MENU_EX);

        // then
        assertThat(findMenus.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("메뉴 아이디로 메뉴 조회")
    void findMenuById() {
        // given
        assertThatThrownBy(() -> menuRepository.findById(1L))
            .isInstanceOf(EmptyResultDataAccessException.class);
        assertThatThrownBy(() -> menuRepository.findById(2L))
            .isInstanceOf(EmptyResultDataAccessException.class);

        // when
        MenuSaveDTO menuA = new MenuSaveDTO(MENU_A, 1); // ID = 1
        MenuSaveDTO menuB = new MenuSaveDTO(MENU_B, 1); // ID = 2
        MenuSaveDTO menuC = new MenuSaveDTO(MENU_C, 1); // ID = 3

        menuRepository.save(menuA);
        menuRepository.save(menuB);
        menuRepository.save(menuC);

        Menu findMenuA = menuService.findMenu(1L);
        Menu findMenuB = menuService.findMenu(2L);
        Menu findMenuC = menuService.findMenu(3L);

        // then
        assertThat(findMenuA.getName()).isEqualTo(MENU_A);
        assertThat(findMenuB.getName()).isEqualTo(MENU_B);
        assertThat(findMenuC.getName()).isEqualTo(MENU_C);
    }
}