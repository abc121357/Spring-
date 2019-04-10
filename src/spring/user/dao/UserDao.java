package spring.user.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public abstract class UserDao {

	//userDao데이터베이스에 user데이터를 입력하는 add함수를 생성
	public void add(User user)throws ClassNotFoundException,SQLException{
	//먼저 드라이버설정
		//서버 연결 객체 생성
		Connection c=getConnection();
		//연결객체의 프리페어스테이트먼트를 사용해서 조작어 저장
		PreparedStatement ps=c.prepareStatement("insert into users(id,name,password) value(?,?,?)");
		//셋스트링을 이용해서 ps에 설정
		ps.setString(1,user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		//설정한 유저정보를 업데이트
		ps.executeUpdate();
	
	//일이 끝났으니 커넥션을 닫는다
	ps.close();
	c.close();
		
	
	}
	

	//아이디값을 입력해서 데이터베이스 아이디 정보를 얻어오는 get메소드
	
	public User get(String id)throws ClassNotFoundException,SQLException{

		Connection c=getConnection();
		//조작어 명령문을 ps에 보냄
		PreparedStatement ps=c.prepareStatement("Select * from users where id=?");
		//먼저 아이디값을 설정
		ps.setString(1, id);
		//아이디 정보를 불러오기 위해 리슐트셋을 만듬
		ResultSet rs=ps.executeQuery();
		rs.next();
		//리턴할 아이디 정보객체를 생성
		User user=new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("Name"));
		user.setPassword(rs.getString("password"));
		//첫번째로 초기화함
		//할게 다끝나면 닫기는 필수
		rs.close();
		ps.close();
		c.close();
		
		
		
		return user;
	}
	//같은 내용을 하나로 합쳐서 코드를 줄이기 위한 방법이다.
	//getConnection()메소드를 Connection으로 반환해서 각각의 필요한 메소드에 반환한다.
	//이경우엔 커넥션을 연결하기 위해 반환한다.
	private abstract Connection getConnection()throws SQLException,ClassNotFoundException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c=DriverManager.getConnection("jdbc:mysql//localhost/3306","root","1234");
		
		
		return c;
	}

	//Connection은 서버와 연결하는역할을한다
	//PreparedStatement는 데이터베이스에 명령문을 집어넣는다
	//ps.setString은 정보를 입력한다
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		UserDao dao= new UserDao();
		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId()+"등록 성공");
		
		User user2=dao.get(user.getId());	
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		System.out.println(user2.getId()+"조회 성공");

		
		
	}
	
	
	
}
