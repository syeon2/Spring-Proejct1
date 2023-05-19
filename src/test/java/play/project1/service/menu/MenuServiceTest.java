package play.project1.service.menu;

import static org.assertj.core.api.Assertions.*;
import static play.project1.domain.menu.Category.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        MenuSaveDTO menuA = new MenuSaveDTO(MENU_A, 1); // ID = 1
        MenuSaveDTO menuB = new MenuSaveDTO(MENU_B, 1); // ID = 2
        MenuSaveDTO menuC = new MenuSaveDTO(MENU_C, 1); // ID = 3

        menuRepository.save(menuA);
        menuRepository.save(menuB);
        menuRepository.save(menuC);

        // when
        Menu findMenuA = menuService.findMenu(1L);
        Menu findMenuB = menuService.findMenu(2L);
        Menu findMenuC = menuService.findMenu(3L);

        // then
        assertThat(findMenuA.getName()).isEqualTo(MENU_A);
        assertThat(findMenuB.getName()).isEqualTo(MENU_B);
        assertThat(findMenuC.getName()).isEqualTo(MENU_C);
    }

    @Test
    @DisplayName("메뉴 없을 때 메뉴 아이디로 조회시 에러 반환")
    void findMenuByIdException() {
        assertThatThrownBy(() -> menuRepository.findById(1L))
            .isInstanceOf(EmptyResultDataAccessException.class);
        assertThatThrownBy(() -> menuRepository.findById(2L))
            .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("메뉴 비동기 카운트 1")
    void addTotalCountAsync() throws InterruptedException {
        // given
        MenuSaveDTO menuA = new MenuSaveDTO(MENU_A, 1);
        menuRepository.save(menuA);

        // when
        Menu findMenuA = menuRepository.findById(1L);
        assertThat(findMenuA.getName()).isEqualTo(MENU_A);

        menuService.addTotalCount(findMenuA, 1);

        // 비동기 처리
        Thread.sleep(1000);

        // then
        Menu updateMenuA = menuRepository.findById(1L);

        assertThat(updateMenuA.getTotalOrder()).isEqualTo(1);
    }

    @Test
    @DisplayName("메뉴 비동기 카운트 10000")
    void addTotalCountAsync2() throws InterruptedException {
        // given
        MenuSaveDTO menuA = new MenuSaveDTO(MENU_A, 1);
        menuRepository.save(menuA);

        // when
        Menu findMenuA = menuRepository.findById(1L);
        assertThat(findMenuA.getName()).isEqualTo(MENU_A);

        int count = 10000;
        ExecutorService service = Executors.newFixedThreadPool(30);
        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            service.submit(() -> {
                try {
                    menuService.addTotalCount(findMenuA, 1);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        // 비동기 처리
        Thread.sleep(1000);

        // then
        Menu updateMenuA = menuRepository.findById(1L);
        assertThat(updateMenuA.getTotalOrder()).isEqualTo(count);
    }
}