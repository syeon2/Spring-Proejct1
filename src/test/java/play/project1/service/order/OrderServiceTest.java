package play.project1.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import play.project1.repository.menu.MenuRepository;
import play.project1.repository.order.OrderDetailRepository;
import play.project1.repository.order.OrderListRepository;
import play.project1.service.member.MemberService;

@SpringBootTest
@Sql(scripts = {"classpath:schema_mysql.sql"})
class OrderServiceTest {

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MenuRepository menuRepository;


}