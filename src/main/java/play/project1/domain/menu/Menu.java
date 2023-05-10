package play.project1.domain.menu;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Menu {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String MENU_CODE = "menu_code";
	public static final String PRICE = "price";
	public static final String TOTAL_ORDER = "total_order";

	private final Long id;
	private final String name;
	private final Integer menuCode;
	private final BigDecimal price;
	private final Long totalOrder;
}
