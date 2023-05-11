package play.project1.service.member.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChargePointDTO {

	private String id;
	private BigDecimal point;
}
