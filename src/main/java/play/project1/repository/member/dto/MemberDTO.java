package play.project1.repository.member.dto;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class MemberDTO {

	private String userId;
	private String name;
	private BigDecimal point;

	public MemberDTO(String userId, String name, BigDecimal point) {
		this.userId = userId;
		this.name = name;
		this.point = point;
	}
}
