package play.project1.repository;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import play.project1.domain.member.Member;
import play.project1.repository.member.dto.MemberDTO;
import play.project1.repository.member.MemberRepository;

@Transactional
@SpringBootTest
class MySQLMemberRepositoryTest {

	@Autowired
	MemberRepository repository;

	@Test
	@DisplayName("회원 저장, 수정, 조회")
	void CRUD() {
		String userId = "hello@gmail.com";
		Member member = new Member(1L, userId, "suyeon", BigDecimal.ONE);

		// save
		repository.save(member);

		// find
		Member findMember = repository.findById(1L);
		assertThat(member.getUserId()).isEqualTo(findMember.getUserId());
		//
		// udpate
		repository.update(member.getId(), new MemberDTO(userId, "suyeon", BigDecimal.valueOf(20000)));
		Member updateMember = repository.findById(member.getId());
		assertThat(updateMember.getPoint()).isEqualTo(BigDecimal.valueOf(20000));
		//
		// delete
		repository.delete(updateMember.getId());
	}
}