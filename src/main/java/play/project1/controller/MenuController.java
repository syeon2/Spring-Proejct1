package play.project1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import play.project1.domain.menu.Menu;
import play.project1.service.menu.MenuService;

@RestController
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/menu")
	public List<Menu> menu(@RequestParam String name) {
		return menuService.findMenus(name);
	}

	@GetMapping("/menu/{menuId}")
	public ResponseEntity<Menu> menu(@PathVariable Long menuId) {
		Menu menu = menuService.findMenu(menuId);

		if (menu == null) return new ResponseEntity<Menu>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Menu>(menu, HttpStatus.OK);
	}
}
