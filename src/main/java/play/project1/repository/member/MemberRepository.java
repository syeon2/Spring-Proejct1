package play.project1.repository.member;

import play.project1.domain.member.Member;
import play.project1.repository.member.dto.MemberDTO;

public interface MemberRepository {

	Member save(Member member);
	Member findById(Long id);
	void update(Long memberId, MemberDTO memberDTO);
	void delete(Long memberId);
}
