package play.project1.service.menu;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import play.project1.domain.menu.Menu;
import play.project1.repository.menu.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final MenuRepository menuRepository;

	public List<Menu> findMenus(String name) {
		return menuRepository.findByName(name);
	}

	public Menu findMenu(Long menuId) {
		return menuRepository.findById(menuId);
	}
}
