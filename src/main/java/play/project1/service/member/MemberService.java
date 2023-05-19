package play.project1.service.member;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import play.project1.domain.member.Member;
import play.project1.dto.member.ChargePointDTO;
import play.project1.dto.member.MemberUpdateDTO;
import play.project1.error.exception.order.PointShortageException;
import play.project1.repository.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public void chargePoint(ChargePointDTO chargePointDTO) {
		String memberId = chargePointDTO.getId();
		BigDecimal point = chargePointDTO.getPoint();

		Member findMember = memberRepository.findById(memberId);
		BigDecimal addedPoint = findMember.getPoint().add(point);

		MemberUpdateDTO memberDTO = new MemberUpdateDTO(findMember.getName(), addedPoint);

		memberRepository.update(findMember.getId(), memberDTO);
	}

	@Transactional
	public void usePoints(BigDecimal totalPoint, String memberId) {
		Member member = memberRepository.findById(memberId);
		if (member.getPoint().compareTo(totalPoint) < 0) throw new PointShortageException("포인트가 부족합니다.");

		memberRepository.update(member.getId(), new MemberUpdateDTO(member.getName(), member.getPoint().subtract(totalPoint)));
	}
}
