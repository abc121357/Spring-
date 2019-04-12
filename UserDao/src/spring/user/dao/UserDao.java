package spring.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao {
	//드라이버 연결 멤버변수를 만든다.
	private ConnectionMaker connectionMaker;
	
	UserDao(){}
	//원하는 드라이버를 지정하기위해 매개변수를 가져온다.
	UserDao(ConnectionMaker connectionMaker){
		this.connectionMaker=connectionMaker;
	}
	
	//데이터베이스를 불러와서 값을 입력하는 add 메소드
	public void add(User user)throws ClassNotFoundException,SQLException{
		Connection c=connectionMaker.makeConnection();
		//데이터베이스에 질의문을 입력
		PreparedStatement ps=c.prepareStatement("insert into users(id,name,password) value(?,?,?)");
		//preparedStatement클래스 객체인 ps에 user id,name,password정보를 입력한다.
		ps.setString(1,user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		//입력후 커밋을 하기 위해 쿼리를 업데이트한다.
			ps.executeUpdate();
		//순서대로 닫는다
		ps.close();
		c.close();
		
	}
	
	public User get(String id)throws ClassNotFoundException,SQLException{
		//커넥션 c에 UserDaoTest에서 불러오는 connectinMaker안의 makeConnection을 불러온다.
		Connection c=connectionMaker.makeConnection();
		//c데이터베이스 질의문을 rs에 입력한다.
		PreparedStatement ps=c.prepareStatement("Select * from users where id=?");
		//?값을 설정한다.
		ps.setString(1,id);
		//값을 읽어오기 위해 resultset을 불러온다.
		ResultSet rs=ps.executeQuery();
		//설정하기 위해 첫번째로이동시키고
		rs.next();
		//값을 읽어오기 위해 데이터질의문이 저장된 rs안의 id값,name값,password값을 getString으로 불러온다.
		User user=new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		
		
		//옮기고 닫고 리턴값을 반환한다.
		rs.close();
		ps.close();
		c.close();
		
		return user;
		
	}
	
	

	//같은 내용을 하나로 합쳐서 코드를 줄이기 위한 방법이다.
	//getConnection()메소드를 Connection으로 반환해서 각각의 필요한 메소드에 반환한다.
	//이경우엔 커넥션을 연결하기 위해 반환한다.

	//Connection은 서버와 연결하는역할을한다
	//PreparedStatement는 데이터베이스에 명령문을 집어넣는다
	//ps.setString은 정보를 입력한다
//end of main
	
	
	
}//end of class
