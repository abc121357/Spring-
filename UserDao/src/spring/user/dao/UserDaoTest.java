package spring.user.dao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import java.sql.SQLException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


import springbook.user.domain.User;

public class UserDaoTest {
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException{
		//메인에서 ConnectionMaker 객체를 만들어서 UserDao에 넘겨준다.
		//생성자에서 ConnectionMaker파라미터를 받을 수 있게 UserDao에서 UserDao자신 멤버변수에 생성자를 실행했다. 그러므로 메인에서 드라이버를 지정가능하다. -1
		//이제 userDao를 메인에서 직접 가져오는것이아니고 DaoFactory를 거쳐 userDao 객체를 만들어서 메인은 UserDao를 실행시키는 역할만을 가진다. -2
		//스프링으로 빈을 불러오는 방식을 사용해본다. -3
		//xml파일 설정해서 루트를 지정하여
		ApplicationContext context=new GenericXmlApplicationContext("applicationContext.xml");
		//DaoFactory클래스를 context이름의 컨테이너로 만들고 (메소드)빈을 불러온다.
		UserDao dao=context.getBean("userDao",UserDao.class);
		dao.deleteAll();
		assertThat(dao.getCount(),is(0));
		
		User user = new User("white","snow","whiteSnow");
		User user1 = new User("winner","looser","king");
		User user2 = new User("sagisawa","Fumika","book");
		
		dao.add(user);
		assertThat(dao.getCount(),is(1));
		dao.add(user1);
		assertThat(dao.getCount(),is(2));
		dao.add(user2);
		assertThat(dao.getCount(),is(3));

		System.out.println(user.getId()+"등록 성공");
		System.out.println(user1.getId()+"등록 성공");
		System.out.println(user2.getId()+"등록 성공");
		
		User user4=dao.get(user2.getId());	
		System.out.println(user4.getName());
		System.out.println(user4.getPassword());
		System.out.println(user4.getId()+"조회 성공");
		//user2.name이 user4.name과 같지 않다면 테스트 실패를 출력한다.
		assertThat(user2.getName(),is(user4.getName()));
		assertThat(user2.getPassword(),is(user4.getPassword()));
		
		//CountingConnectionMaker ccm= context.getBean("dataSource",CountingConnectionMaker.class);
		//System.out.println("Connection counter : " + ccm.get());
	}
	
	public static void main(String args[]) {
		JUnitCore.main("spring.user.dao.UserDaoTest");
	}
	
}
