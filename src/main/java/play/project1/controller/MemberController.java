package play.project1.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import play.project1.service.member.MemberService;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/charge")
	public ResponseEntity<Void> chargingPoint(Long memberId, BigDecimal point) {
		if (point.intValue() <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		memberService.chargePoint(memberId, point);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
