package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Hello;
import study.querydsl.entity.QHello;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Commit	// 테스트는 @Transactional 일때 무조건 쿼리 롤백시키기 때문에 테스트 값 db 저장하려면 @Commit 달아주기
class QuerydslApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {
		// hello entity 저장
		Hello hello = new Hello();
		em.persist(hello);

		// 쿼리 날리기
		JPAQueryFactory query = new JPAQueryFactory(em);
		QHello qHello = new QHello("h");	// alias 는 h 로 지정

		Hello result = query
				.selectFrom(qHello)
				.fetchOne();

		// 검증
		Assertions.assertThat(result).isEqualTo(hello);
		// 롬복 검증
		Assertions.assertThat(result.getId()).isEqualTo(hello.getId());
	}

}
