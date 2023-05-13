package play.project1.repository.member;

import static play.project1.domain.member.Member.*;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import play.project1.domain.member.Member;
import play.project1.dto.member.MemberSaveDTO;
import play.project1.dto.member.MemberUpdateDTO;

@Repository
public class JdbcMemberRepository implements MemberRepository {

	private final JdbcTemplate template;

	public JdbcMemberRepository(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public Member save(MemberSaveDTO memberSaveDTO) {
		String sql = "insert into member(id, name) values (?, ?)";

		template.update(sql, memberSaveDTO.getId(),memberSaveDTO.getName());

		return Member.createNewMember(memberSaveDTO.getId(), memberSaveDTO.getName());
	}

	@Override
	public Member findById(String memberId) {
		String sql = "select * from member where id = ?";

		return template.queryForObject(sql, memberRowMapper(), memberId);
	}

	private RowMapper<Member> memberRowMapper() {
		return ((rs, rowNum) -> new Member(
			rs.getString(ID),
			rs.getString(NAME),
			rs.getBigDecimal(POINT)));
	}

	@Override
	public void update(String memberId, MemberUpdateDTO memberDTO) {
		String sql = "update member set name = ?, point = ? where id = ?";

		template.update(sql, memberDTO.getName(), memberDTO.getPoint(), memberId);
	}

	@Override
	public void delete(String memberId) {
		String sql = "delete from member where id = ?";

		template.update(sql, memberId);
	}
}
