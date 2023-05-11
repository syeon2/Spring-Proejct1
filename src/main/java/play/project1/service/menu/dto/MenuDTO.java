package play.project1.service.menu.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuDTO {

	private final String name;
	private final BigDecimal price;
	private final Integer menuCode;
	private final Long totalOrder;
}
