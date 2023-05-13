package play.project1.domain.order;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetail {

	private final Long id;
	private final Long orderId;
	private final String memberId;
	private final Long menuId;
	private final Integer count;
	private final BigDecimal price;
}
