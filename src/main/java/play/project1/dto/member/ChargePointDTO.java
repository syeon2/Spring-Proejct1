package play.project1.dto.member;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChargePointDTO {

	@NotNull
	@Email
	private String id;

	@NotNull
	@Min(0)
	private BigDecimal point;
}
