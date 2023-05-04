package play.project1.domain.member;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Member {

	public static final String ID = "id";
	public static final String USER_ID = "user_id";
	public static final String NAME = "name";
	public static final String POINT = "point";

	private final Long id;
	private final String userId;
	private final String name;
	private final BigDecimal point;
}
