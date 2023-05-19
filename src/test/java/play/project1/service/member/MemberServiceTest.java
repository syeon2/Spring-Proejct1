package play.project1.service.member;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import play.project1.domain.member.Member;
import play.project1.dto.member.ChargePointDTO;
import play.project1.dto.member.MemberSaveDTO;
import play.project1.error.exception.order.PointShortageException;
import play.project1.repository.member.MemberRepository;

@SpringBootTest
@Sql(scripts = {"classpath:schema_mysql.sql"})
class MemberServiceTest {

    private final String MEMBER_ID = "waterkite94@gmail.com";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 포인트 충전")
    void chargePoint() {
        // given
        Member member = memberRepository.save(new MemberSaveDTO(MEMBER_ID, "수연"));
        ChargePointDTO chargePointDTO = new ChargePointDTO(MEMBER_ID, BigDecimal.valueOf(20000));

        // when
        memberService.chargePoint(chargePointDTO);

        // then
        Member findMember = memberRepository.findById(MEMBER_ID);
        assertThat(findMember.getPoint()).isEqualTo(BigDecimal.valueOf(20000));
    }

    @Test
    @DisplayName("회원 포인트 충전 동시성 테스트")
    void ChargePointSynchronization() throws InterruptedException {
        // given
        Member member = memberRepository.save(new MemberSaveDTO(MEMBER_ID, "수연"));
        Member findMember = memberRepository.findById(MEMBER_ID);
        assertThat(findMember.getPoint()).isEqualTo(BigDecimal.ZERO);

        // when
        int price = 10000;
        ChargePointDTO chargePointDTO = new ChargePointDTO(MEMBER_ID, BigDecimal.valueOf(price));

        int count = 100;
        ExecutorService service = Executors.newFixedThreadPool(count);
        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            service.submit(() -> {
                try {
                    memberService.chargePoint(chargePointDTO);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        // then
        Member updateMember = memberRepository.findById(MEMBER_ID);
        assertThat(updateMember.getPoint().intValue()).isEqualTo(price * count);
    }

    @Test
    @DisplayName("회원 포인트 사용")
    void usePoint() {
        // given
        Member member = memberRepository.save(new MemberSaveDTO(MEMBER_ID, "수연"));
        ChargePointDTO chargePointDTO = new ChargePointDTO(MEMBER_ID, BigDecimal.valueOf(20000));
        memberService.chargePoint(chargePointDTO);

        // when
        memberService.usePoints(BigDecimal.valueOf(10000), MEMBER_ID);

        // then
        Member findMember = memberRepository.findById(MEMBER_ID);
        assertThat(findMember.getPoint()).isEqualTo(BigDecimal.valueOf(10000));
    }

    @Test
    @DisplayName("회원 포인트 사용 동시성 테스트")
    void usePointSynchronization() throws InterruptedException {
        // given
        int count = 1000;
        int price = 10000;
        
        Member member = memberRepository.save(new MemberSaveDTO(MEMBER_ID, "수연"));
        ChargePointDTO chargePointDTO = new ChargePointDTO(MEMBER_ID, BigDecimal.valueOf(price * count));
        memberService.chargePoint(chargePointDTO);

        // when
        ExecutorService service = Executors.newFixedThreadPool(30);
        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            service.submit(() -> {
                try {
                    memberService.usePoints(BigDecimal.valueOf(price), MEMBER_ID);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        
        // then
        Member findMember = memberRepository.findById(MEMBER_ID);
        assertThat(findMember.getPoint().intValue()).isEqualTo(0);
    }

    @Test
    @DisplayName("회원 포인트 부족 시, 사용하면 에러 Throw")
    void usePoint_X() {
        // given
        Member member = memberRepository.save(new MemberSaveDTO(MEMBER_ID, "수연"));
        Member findMember = memberRepository.findById(MEMBER_ID);
        assertThat(findMember.getPoint()).isEqualTo(BigDecimal.ZERO);

        // when
        assertThatThrownBy(() -> memberService.usePoints(BigDecimal.valueOf(20000), MEMBER_ID))
            .isInstanceOf(PointShortageException.class);

        // then
        Member updatedMember = memberRepository.findById(MEMBER_ID);
        assertThat(updatedMember.getPoint()).isEqualTo(BigDecimal.ZERO);
    }


}