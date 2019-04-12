package spring.user.dao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DaoFactory {
	//DaoFactory에서 UserDao를 쓰기 위한 메소드를 만든다.
	@Bean
	public UserDao userDao() {
		
	return new UserDao(connectionMaker());
	}
	//userDao의 역할을 분할해서 설정한다.
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
	
	
}
