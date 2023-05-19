package play.project1.service.order;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import play.project1.domain.member.Member;
import play.project1.domain.menu.Menu;
import play.project1.dto.menu.MenuSaveDTO;
import play.project1.dto.order.OrderDetailRequestDTO;
import play.project1.dto.order.OrderRequestDTO;
import play.project1.repository.member.MemberRepository;
import play.project1.repository.menu.MenuRepository;
import play.project1.repository.order.OrderDetailRepository;
import play.project1.repository.order.OrderListRepository;
import play.project1.service.member.MemberService;

@SpringBootTest
@Sql(scripts = {"classpath:schema_mysql.sql", "classpath:data-mysql.sql"})
class OrderServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private OrderService orderService;

    @Test
    void createOrder() throws InterruptedException {
        // given
        Menu menu = menuRepository.findById(1L);
        Map<Menu, Integer> menuMap = new HashMap<>();
        menuMap.put(menu, 1);

        String memberId = "4568ksy@naver.com";
        OrderDetailRequestDTO orderDetailRequestDTO = new OrderDetailRequestDTO(1L, 1);
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(memberId, List.of(orderDetailRequestDTO));

        // when
        int count = 100;
        ExecutorService service = Executors.newFixedThreadPool(30);
        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            try {
                orderService.createOrder(menuMap, orderRequestDTO);
            } finally {
                latch.countDown();
            }
        }

        latch.await();

        // then
        Menu findMenu = menuRepository.findById(1L);
        assertThat(findMenu.getTotalOrder()).isEqualTo(count);

        Member findMember = memberRepository.findById(memberId);
        assertThat(findMember.getPoint().intValue()).isEqualTo(0);
    }
}