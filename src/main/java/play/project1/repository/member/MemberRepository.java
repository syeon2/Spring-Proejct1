package play.project1.repository.member;

import play.project1.domain.member.Member;
import play.project1.dto.member.MemberSaveDTO;
import play.project1.dto.member.MemberUpdateDTO;

public interface MemberRepository {

	Member save(MemberSaveDTO memberSaveDTO);
	Member findById(String memberId);
	void update(String memberId, MemberUpdateDTO memberDTO);
	void delete(String memberId);
}
