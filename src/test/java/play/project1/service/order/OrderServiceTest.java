package play.project1.service.order;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import play.project1.domain.member.Member;
import play.project1.domain.menu.Menu;
import play.project1.dto.member.ChargePointDTO;
import play.project1.dto.member.MemberSaveDTO;
import play.project1.dto.menu.MenuSaveDTO;
import play.project1.dto.order.OrderDetailRequestDTO;
import play.project1.dto.order.OrderRequestDTO;
import play.project1.repository.member.MemberRepository;
import play.project1.repository.menu.MenuRepository;
import play.project1.service.member.MemberService;

@SpringBootTest
@Sql(scripts = {"classpath:schema_mysql.sql"})
class OrderServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderService orderService;

    private static final String MENU_A = "menuA";
    private static final String MEMBER_ID = "4568ksy@naver.com";
    private static final Integer PRICE = 4000;
    private static final Integer COUNT = 100;

    @BeforeEach
    void before() {
        menuRepository.save(new MenuSaveDTO(MENU_A, 1, 4000));
        memberRepository.save(new MemberSaveDTO(MEMBER_ID, "suyeon"));
        memberService.chargePoint(new ChargePointDTO(MEMBER_ID, BigDecimal.valueOf(COUNT * PRICE)));
    }

    @Test
    @DisplayName("주문 생성 - 동시성 처리 테스트 100개")
    void createOrder() throws InterruptedException {
        // given
        Menu findMenu = menuRepository.findById(1L);
        Map<Menu, Integer> menuMap = new HashMap<>();
        menuMap.put(findMenu, 1);

        OrderDetailRequestDTO orderDetailRequestDTO = new OrderDetailRequestDTO(1L, 1);
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(MEMBER_ID, List.of(orderDetailRequestDTO));

        // when
        ExecutorService service = Executors.newFixedThreadPool(30);
        CountDownLatch latch = new CountDownLatch(COUNT);

        for (int i = 0; i < COUNT; i++) {
            service.submit(() -> {
                try {
                    orderService.createOrder(menuMap, orderRequestDTO);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        Menu updateMenu = menuRepository.findById(1L);
        assertThat(updateMenu.getTotalOrder().intValue()).isEqualTo(COUNT);

        Member updateMember = memberRepository.findById(MEMBER_ID);
        assertThat(updateMember.getPoint().intValue()).isEqualTo(0);
    }
}