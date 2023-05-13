package play.project1.domain.member;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class Member {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String POINT = "point";

	private final String id;
	private final String name;
	private final BigDecimal point;

	private Member(String id, String name) {
		this.id = id;
		this.name = name;
		this.point = BigDecimal.ZERO;
	}

	public static Member createNewMember(String id, String name) {
		return new Member(id, name);
	}
}
