package play.project1.repository.member;

import play.project1.domain.member.Member;
import play.project1.service.member.dto.MemberDTO;

public interface MemberRepository {

	Member save(Member member);
	Member findById(String memberId);
	void update(String memberId, MemberDTO memberDTO);
	void delete(String memberId);
}
