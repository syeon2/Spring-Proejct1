package play.project1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import play.project1.domain.menu.Menu;
import play.project1.service.menu.MenuService;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping
	public List<Menu> menuList(String name) {
		return menuService.findMenus(name);
	}

	@GetMapping("/{menuId}")
	public Menu menu(@PathVariable Long menuId) {
		return menuService.findMenu(menuId);
	}

	@GetMapping("/popular")
	public List<Menu> popularMenu() {
		return menuService.getRankingList();
	}
}
