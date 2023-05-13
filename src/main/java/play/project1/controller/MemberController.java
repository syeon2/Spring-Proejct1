package play.project1.controller;

import static play.project1.util.constURL.MemberURLConst.*;
import static play.project1.util.constURL.MemberURLConst.UpdateURL.*;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import play.project1.dto.member.ChargePointDTO;
import play.project1.service.member.MemberService;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping(CHARGE_URL)
	public ChargePointDTO chargePoint(@Validated @RequestBody ChargePointDTO chargePointDTO) {
		memberService.chargePoint(chargePointDTO);

		return chargePointDTO;
	}
}
