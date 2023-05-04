package play.project1.repository.member;

import static play.project1.domain.member.Member.*;

import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import play.project1.domain.member.Member;
import play.project1.repository.member.dto.MemberDTO;

@Repository
public class MySQLMemberRepository implements MemberRepository {

	private final JdbcTemplate template;

	public MySQLMemberRepository(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public Member save(Member member) {
		String sql = "insert into member(USER_ID, NAME) values (?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update((connection) -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] {ID});
			ps.setString(1, member.getUserId());
			ps.setString(2, member.getName());

			return ps;
		}, keyHolder);

		long id = keyHolder.getKey().longValue();
		return new Member(id, member.getUserId(), member.getName(), member.getPoint());
	}

	@Override
	public Member findById(Long id) {
		String sql = "select * from member where id = ?";

		return template.queryForObject(sql, memberRowMapper(), id);
	}

	private RowMapper<Member> memberRowMapper() {
		return ((rs, rowNum) -> new Member(
			rs.getLong(ID),
			rs.getString(USER_ID),
			rs.getString(NAME),
			rs.getBigDecimal(POINT)));
	}

	@Override
	public void update(Long memberId, MemberDTO memberDTO) {
		String sql = "update member set user_id = ?, name = ?, point = ? where id = ?";

		template.update(sql, memberDTO.getUserId(), memberDTO.getName(), memberDTO.getPoint(), memberId);
	}

	@Override
	public void delete(Long memberId) {
		String sql = "delete from member where id = ?";

		template.update(sql, memberId);
	}
}
