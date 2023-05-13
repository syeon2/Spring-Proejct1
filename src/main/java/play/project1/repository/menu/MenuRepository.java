package play.project1.repository.menu;

import java.util.List;

import play.project1.domain.menu.Menu;
import play.project1.dto.menu.MenuSaveDTO;
import play.project1.dto.menu.MenuUpdateDTO;

public interface MenuRepository {

	Menu save(MenuSaveDTO menuSaveDTO);
	List<Menu> findByName(String name);
	Menu findById(Long menuId);
	void update(Long menuId, MenuUpdateDTO menuDTO);
	void delete(Long menuId);
}
