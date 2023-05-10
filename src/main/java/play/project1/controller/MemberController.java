package play.project1.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import play.project1.repository.member.dto.ChargePointDTO;
import play.project1.service.member.MemberService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/charge")
	public ResponseEntity<Void> chargingPoint(@RequestBody ChargePointDTO chargePointDTO) {
		BigDecimal point = chargePointDTO.getPoint();

		if (point.intValue() <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		memberService.chargePoint(chargePointDTO.getId(), point);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
