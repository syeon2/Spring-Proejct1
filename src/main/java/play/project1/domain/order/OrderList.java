package play.project1.domain.order;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderList {

	private final Long id;
	private final String memberId;
	private final Integer menuCount;
	private final BigDecimal totalPrice;
}