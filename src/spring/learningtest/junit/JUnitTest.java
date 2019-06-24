package spring.learningtest.junit;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import springbook.user.domain.User;

import org.junit.Test;


public class JUnitTest {
	
	static JUnitTest junit;
	
	@Test
	public void junitTest1(){
		assertThat(this,is(not(sameInstance(junit))));
		junit=this.junit;
	}
	
	
}

/*2. 테스트 정리
테스트는 자동화되야 하고, 빠르게 실행할 수 있어야 한다.
main()테스트대신 jUnit 프레임워크를 이용한 테스트 작성이 편리하다.
테스트 결과는 일관성이 있어야 한다. 코드의 변경없이 환경이나 테스트 실행 순서에 따라 결과가 달라지면 안된다.
테스트는 포괄적으로 작성해야 한다. 충분한 검증을 하지 않는 테스트는 없는 것보다 나쁠 수 있다.
코드 작성과 테스트 수행의 간격이 짧을수록 효과적이다.
테스트 하기 쉬운 코드가 좋은 코드다.
테스트를 먼저 만들고 테스트를 성공시키는 코드를 만들어가는 테스트 주도 개발 방법도 유용하다.
테스트 코드도 애플리케이션 코드와 마찬가지로 적절한 리팩토링이 필요하다.
@Before, @After를 사용해서 테스트 메소드들의 공통 준비 작업과 정리 작업을 처리할 수 있다.
스프링 테스트 컨텍스트 프레임워크를 이용하면 테스트 성능을 향상시킬 수 있다.
동일한 설정파일을 사용하는 테스트는 하나의 애플리케이션 컨텍스트를 공유한다.
@Autowired를 사용하면 컨텍스트의 빈을 테스트 오브젝트에 DI할 수 있다.
기술의 사용 방법을 익히고 이해를 돕기 위해 학습 테스트를 작성하자
오류가 발견될 경우 그에 대한 버그 테스트를 만들어두면 유용하다.

*/