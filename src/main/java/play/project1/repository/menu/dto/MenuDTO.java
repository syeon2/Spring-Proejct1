package play.project1.repository.menu.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuDTO {

	private final String name;
	private final int price;
	private final int menuCode;
	private final int orderCount;
}
