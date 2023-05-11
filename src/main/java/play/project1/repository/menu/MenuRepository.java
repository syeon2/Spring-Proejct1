package play.project1.repository.menu;

import java.util.List;

import play.project1.domain.menu.Menu;
import play.project1.service.menu.dto.MenuDTO;

public interface MenuRepository {

	Menu save(Menu menu);
	List<Menu> findByName(String name);
	Menu findById(Long menuId);
	void update(Long menuId, MenuDTO menuDTO);
	void delete(Long menuId);
}
