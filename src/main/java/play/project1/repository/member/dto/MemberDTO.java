package play.project1.repository.member.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberDTO {

	private final String name;
	private final BigDecimal point;
}
