package spring.user.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import spring.user.domain.Level;
import spring.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

//스프
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {
	@Autowired
	private UserDao dao;
	private User user;
	private User user1;
	private User user2;
	private User user3;

	@Autowired
	DataSource dataSource;
	/*public void main2() throws ClassNotFoundException, SQLException{
		//메인에서 ConnectionMaker 객체를 만들어서 UserDao에 넘겨준다.
		//생성자에서 ConnectionMaker파라미터를 받을 수 있게 UserDao에서 UserDao자신 멤버변수에 생성자를 실행했다. 그러므로 메인에서 드라이버를 지정가능하다. -1
		//이제 userDao를 메인에서 직접 가져오는것이아니고 DaoFactory를 거쳐 userDao 객체를 만들어서 메인은 UserDao를 실행시키는 역할만을 가진다. -2
		//스프링으로 빈을 불러오는 방식을 사용해본다. -3
		//xml파일 설정해서 루트를 지정하여 UserDao dao에 의존관계를 주입한다.
		ApplicationContext context=new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao=context.getBean("userDao",UserDao.class);
		dao.deleteAll();
		assertThat(dao.getCount(),is(0));
		
		User user = new User("white","snow","whiteSnow");
		User user1 = new User("winner","looser","king");
		User user2 = new User("sagisawa","Fumika","book");
		
		dao.add(user);
		assertThat(dao.getCount(),is(1));
		assertThat(user.getName(),is(user.getName()));
		assertThat(user.getPassword(),is(user.getPassword()));
		dao.add(user1);
		assertThat(dao.getCount(),is(2));
		assertThat(user1.getName(),is(user1.getName()));
		assertThat(user1.getPassword(),is(user1.getPassword()));
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
	}*/
	
	
	//Before는 테스트코드들의 중복된 내용들을 한번에 모아 실행시키는 코드다.
	@Before
	public void setUp() {
		this.user = new User("white","snow","whiteSnow", Level.BASIC,1,0);
		this.user1 = new User("winner","looser","king",Level.SILVER,55,10);
		this.user2 = new User("sagisawa","Fumika","book",Level.SILVER,100,40);

	}
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException{
		//메인에서 ConnectionMaker 객체를 만들어서 UserDao에 넘겨준다.
		//생성자에서 ConnectionMaker파라미터를 받을 수 있게 UserDao에서 UserDao자신 멤버변수에 생성자를 실행했다. 그러므로 메인에서 드라이버를 지정가능하다. -1
		//이제 userDao를 메인에서 직접 가져오는것이아니고 DaoFactory를 거쳐 userDao 객체를 만들어서 메인은 UserDao를 실행시키는 역할만을 가진다. -2
		//스프링으로 빈을 불러오는 방식을 사용해본다. -3
		//xml파일 설정해서 루트를 지정하여
		dao.deleteAll();
		assertThat(dao.getCount(),is(0));
		System.out.println("0개인지 확인");
		
		
		dao.add(user);
		assertThat(dao.getCount(),is(1));
		assertThat(user.getName(),is(user.getName()));
		assertThat(user.getPassword(),is(user.getPassword()));
		dao.add(user1);
		assertThat(dao.getCount(),is(2));
		assertThat(user1.getName(),is(user1.getName()));
		assertThat(user1.getPassword(),is(user1.getPassword()));
		dao.add(user2);
		assertThat(dao.getCount(),is(3));

		System.out.println(user.getId()+"등록 성공");
		System.out.println(user1.getId()+"등록 성공");
		System.out.println(user2.getId()+"등록 성공");
		
		User userget2=dao.get(user2.getId());
		checkSameUser(userget2, user2);

		System.out.println(userget2.getName());
		System.out.println(userget2.getPassword());
		System.out.println(userget2.getId()+"조회 성공");
		//user2.name이 user4.name과 같지 않다면 테스트 실패를 출력한다.
		assertThat(user2.getName(),is(userget2.getName()));
		assertThat(user2.getPassword(),is(userget2.getPassword()));
		
		//CountingConnectionMaker ccm= context.getBean("dataSource",CountingConnectionMaker.class);
		//System.out.println("Connection counter : " + ccm.get());
	}

	@Test
	public void count()throws SQLException,ClassNotFoundException{
		dao.deleteAll();
		assertThat(dao.getCount(),is(0));
		System.out.println("0개인지 확인");



		dao.add(user);
		assertThat(dao.getCount(),is(1));
		assertThat(user.getName(),is(user.getName()));
		assertThat(user.getPassword(),is(user.getPassword()));


		dao.add(user1);
		assertThat(dao.getCount(),is(2));
		assertThat(user1.getName(),is(user1.getName()));

		assertThat(user1.getPassword(),is(user1.getPassword()));
		dao.add(user2);
		assertThat(dao.getCount(),is(3));

	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws ClassNotFoundException, SQLException{
		//메인에서 ConnectionMaker 객체를 만들어서 UserDao에 넘겨준다.
		//생성자에서 ConnectionMaker파라미터를 받을 수 있게 UserDao에서 UserDao자신 멤버변수에 생성자를 실행했다. 그러므로 메인에서 드라이버를 지정가능하다. -1
		//이제 userDao를 메인에서 직접 가져오는것이아니고 DaoFactory를 거쳐 userDao 객체를 만들어서 메인은 UserDao를 실행시키는 역할만을 가진다. -2
		//스프링으로 빈을 불러오는 방식을 사용해본다. -3
		//xml파일 설정해서 루트를 지정하여
		dao.deleteAll();
		assertThat(dao.getCount(),is(0));
		System.out.println("0개인지 확인");

		dao.add(user);
		assertThat(dao.getCount(),is(1));
		assertThat(user.getName(),is(user.getName()));
		assertThat(user.getPassword(),is(user.getPassword()));
		dao.add(user1);
		assertThat(dao.getCount(),is(2));
		assertThat(user1.getName(),is(user1.getName()));
		assertThat(user1.getPassword(),is(user1.getPassword()));
		dao.add(user2);
		assertThat(dao.getCount(),is(3));

		System.out.println(user.getId()+"등록 성공");
		System.out.println(user1.getId()+"등록 성공");
		System.out.println(user2.getId()+"등록 성공");
		
		User user4=dao.get("humi");	
		System.out.println(user4.getName());
		System.out.println(user4.getPassword());
		System.out.println(user4.getId()+"조회 성공");
		//user2.name이 user4.name과 같지 않다면 테스트 실패를 출력한다.
		assertThat(user2.getName(),is(user4.getName()));
		assertThat(user2.getPassword(),is(user4.getPassword()));
		
		//CountingConnectionMaker ccm= context.getBean("dataSource",CountingConnectionMaker.class);
		//System.out.println("Connection counter : " + ccm.get());
	}

	@Test
	public void getAll()throws SQLException,ClassNotFoundException{
		dao.deleteAll();


		dao.add(user);
		List<User> users= dao.getAll();
		assertThat(users.size(), is(1));
		checkSameUser(user,users.get(0));

		dao.add(user1);
		List<User> users1= dao.getAll();
		assertThat(users1.size(),is(2));
		checkSameUser(user1,users1.get(0));



		dao.add(user2);
		List<User> users2 = dao.getAll();
		assertThat(users2.size(),is(3));
		checkSameUser(user2,users2.get(0));


	}

	@Test
	public void sqlExceptionTranslate(){
		dao.deleteAll();

		try{
			dao.add(user1);
			dao.add(user1);
		}catch(DuplicateKeyException ex){
			SQLException sqlEx=(SQLException)ex.getRootCause();
			SQLExceptionTranslator set = // 코드를 이용한 SQLException의 전환
					new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

		}
	}




	@Test
	public void update(){
		dao.deleteAll();

		dao.add(user1);
		dao.add(user2);


		user1.setName("humika");
		user1.setPassword("springno6");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);

		dao.update(user1);
		System.out.println(user1.getName());
		System.out.println(user1.getPassword());
		System.out.println(user1.getLevel());

		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		System.out.println(user2.getLevel());

		User user1update = dao.get(user1.getId());
		checkSameUser(user1, user1update);
		User user2same = dao.get(user2.getId());
		checkSameUser(user2,user2same);
	}



	private void checkSameUser(User user1, User user2){
		assertThat(user1.getId(),is(user2.getId()));
		assertThat(user1.getName(),is(user2.getName()));
		assertThat(user1.getPassword(),is(user2.getPassword()));
        assertThat(user1.getLevel(),is(user2.getLevel()));
        assertThat(user1.getRecommend(),is(user2.getRecommend()));

	}
	public static void main(String args[]) {
		JUnitCore.main("spring.user.dao.UserDaoTest");
	}
	
}
