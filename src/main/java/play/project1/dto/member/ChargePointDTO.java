package play.project1.dto.member;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class ChargePointDTO {

	@NotNull
	@Email
	private String id;

	@NotNull
	@Min(0)
	private BigDecimal point;

	public ChargePointDTO() {
	}

	public ChargePointDTO(String id, BigDecimal point) {
		this.id = id;
		this.point = point;
	}
}
