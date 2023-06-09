package play.project1.service.menu;

import static play.project1.util.config.redis.DBKey.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import play.project1.domain.menu.Menu;
import play.project1.dto.menu.MenuUpdateDTO;
import play.project1.repository.menu.MenuRepository;

@Service
@EnableAsync
@RequiredArgsConstructor
public class MenuService {

	private final MenuRepository menuRepository;
	private final RedisTemplate redisTemplate;

	public List<Menu> findMenus(String name) {
		return menuRepository.findByName(name);
	}

	public Menu findMenu(Long menuId) {
		return menuRepository.findById(menuId);
	}


	@Async
	@Transactional
	public void addTotalCount(Menu menu, Integer orderCount) {
		Menu updateMenu = menuRepository.findById(menu.getId());
		menuRepository.update(updateMenu.getId(), MenuUpdateDTO.addTotalCount(updateMenu, orderCount));
	}

	public List<Menu> getRankingList() {
		ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
		Set<String> popularMenuId = stringStringZSetOperations.reverseRange(MENU, 0, 2);

		List<Menu> menuList = new ArrayList<>();
		for (String menuId : popularMenuId) {
			Menu menu = menuRepository.findById(Long.parseLong(menuId));
			menuList.add(menu);
		}

		return menuList;
	}

	public void countMenuToRedis(Menu menu, Integer orderCount) {
		redisTemplate.opsForZSet().incrementScore("menu", String.valueOf(menu.getId()), orderCount);
	}
}
