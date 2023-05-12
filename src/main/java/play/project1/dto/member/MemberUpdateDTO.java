package play.project1.dto.member;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class MemberUpdateDTO {

	private final String name;
	private final BigDecimal point;
}
