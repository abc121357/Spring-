package spring.user.dao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
@Configuration
public class CountingDaoFactory {
	//DaoFactory에서 UserDao를 쓰기 위한 메소드를 만든다.
	@Bean
	public DataSource dataSource() {

		SimpleDriverDataSource dataSource =new SimpleDriverDataSource();

		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost:3036/my_database?serverTimezone=Asia/Seoul");
		dataSource.setUsername("root");
		dataSource.setPassword("gkswn1");
		return dataSource;
	}
	@Bean
	public UserDao userDao() {
		UserDao userDao=new UserDao();
		userDao.setDataSource(dataSource());
	return userDao;
	}
	//userDao의 역할을 분할해서 설정한다.
	@Bean
	public ConnectionMaker connectionMaker() {
		return new CountingConnectionMaker(realConnectionMaker());
	}
	@Bean
	public ConnectionMaker realConnectionMaker() {
		
		return new DConnectionMaker();
	}
	
}
