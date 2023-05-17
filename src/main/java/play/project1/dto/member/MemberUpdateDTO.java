package play.project1.dto.member;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import play.project1.domain.member.Member;

@Getter
@AllArgsConstructor
public class MemberUpdateDTO {

	private final String name;
	private final BigDecimal point;

	public static MemberUpdateDTO createChargePointDTO(Member member, BigDecimal chargePoint) {
		return new MemberUpdateDTO(member.getName(), chargePoint);
	}
}
