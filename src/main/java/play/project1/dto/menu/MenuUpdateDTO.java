package play.project1.dto.menu;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import play.project1.domain.menu.Menu;

@Getter
@RequiredArgsConstructor
public class MenuUpdateDTO {

	private final String name;
	private final BigDecimal price;
	private final Integer menuCode;
	private final Long totalOrder;
}
