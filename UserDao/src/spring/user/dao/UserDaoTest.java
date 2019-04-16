package spring.user.dao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		//메인에서 ConnectionMaker 객체를 만들어서 UserDao에 넘겨준다.
		//생성자에서 ConnectionMaker파라미터를 받을 수 있게 UserDao에서 UserDao자신 멤버변수에 생성자를 실행했다. 그러므로 메인에서 드라이버를 지정가능하다. -1
		//이제 userDao를 메인에서 직접 가져오는것이아니고 DaoFactory를 거쳐 userDao 객체를 만들어서 메인은 UserDao를 실행시키는 역할만을 가진다. -2
		//스프링으로 빈을 불러오는 방식을 사용해본다. -3
		//xml파일 설정해서 루트를 지정하여
		ApplicationContext context=new GenericXmlApplicationContext("applicationContext.xml");
		//DaoFactory클래스를 context이름의 컨테이너로 만들고 (메소드)빈을 불러온다.
		UserDao dao=context.getBean("userDao",UserDao.class);
		User user = new User();
		user.setId("white3");
		//db오류땜시 db에 접속하는 데이터는 일단 모두 영어로 작성
		user.setName("abcdb");
		user.setPassword("goodgoos");
		
		dao.add(user);
		
		System.out.println(user.getId()+"등록 성공");
		
		User user2=dao.get(user.getId());	
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		System.out.println(user2.getId()+"조회 성공");
		
		CountingConnectionMaker ccm= context.getBean("dataSource",CountingConnectionMaker.class);
		//System.out.println("Connection counter : " + ccm.get());
	}
	
	
	
}
