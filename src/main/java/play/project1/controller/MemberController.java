package play.project1.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import play.project1.dto.member.ChargePointDTO;
import play.project1.service.member.MemberService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/charge")
	public ChargePointDTO chargePoint(@Validated @RequestBody ChargePointDTO chargePointDTO) {
		memberService.chargePoint(chargePointDTO);

		return chargePointDTO;
	}
}
