package play.project1.repository.member;

import static play.project1.domain.member.Member.*;
import static play.project1.repository.member.sql.MemberSQL.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import play.project1.domain.member.Member;
import play.project1.dto.member.MemberSaveDTO;
import play.project1.dto.member.MemberUpdateDTO;

@Repository
@RequiredArgsConstructor
public class JdbcMemberRepository implements MemberRepository {

	private final JdbcTemplate template;

	@Override
	public Member save(MemberSaveDTO memberSaveDTO) {
		template.update(INSERT, memberSaveDTO.getId(),memberSaveDTO.getName());

		return Member.createNewMember(memberSaveDTO.getId(), memberSaveDTO.getName());
	}

	@Override
	public Member findById(String memberId) {
		return template.queryForObject(FIND_BY_ID, memberRowMapper(), memberId);
	}

	private RowMapper<Member> memberRowMapper() {
		return ((rs, rowNum) -> new Member(
			rs.getString(ID),
			rs.getString(NAME),
			rs.getBigDecimal(POINT)));
	}

	@Override
	public void update(String memberId, MemberUpdateDTO memberDTO) {
		template.update(UPDATE, memberDTO.getName(), memberDTO.getPoint(), memberId);
	}

	@Override
	public void delete(String memberId) {
		template.update(DELETE, memberId);
	}
}
