package play.project1.service.member;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import play.project1.domain.member.Member;
import play.project1.repository.member.MemberRepository;
import play.project1.repository.member.dto.MemberDTO;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public void chargePoint(Long id, BigDecimal point) {
		Member findMember = memberRepository.findById(id);

		BigDecimal addedPoint = findMember.getPoint().add(point);
		MemberDTO memberDTO = new MemberDTO(findMember.getUserId(), findMember.getName(), addedPoint);

		memberRepository.update(findMember.getId(), memberDTO);
	}
}