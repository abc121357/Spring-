package spring.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;
import javax.sql.DataSource;
public class UserDao {
	//드라이버 연결 멤버변수를 만든다.
	private DataSource dataSource;
	private ConnectionMaker connectionMaker;
	UserDao(){
		//의존관계 주입
		//this.connection=connection
		//DaoFactory클래스이용
		//DaoFactory daoFactory=new DaoFactory();
		//this.connection=DaoFactory.ConnectionMaker();
		//의존관계 검색
		/*ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		this.connectionMaker=context.getBean("connectionMaker",ConnectionMaker.class);*/
	}
	
	//원하는 드라이버를 지정하기위해 매개변수를 가져온다.
	public void setDataSource(DataSource dataSource){
		this.dataSource=dataSource;
	}
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker=connectionMaker;
	}
	
	//데이터베이스를 불러와서 값을 입력하는 add 메소드
	public void add(User user)throws ClassNotFoundException,SQLException{
		
		Connection c=dataSource.getConnection();
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
		Connection c=dataSource.getConnection();
		//c데이터베이스 질의문을 rs에 입력한다.
		PreparedStatement ps=c.prepareStatement("Select * from users where id=?");
		//?값을 설정한다.
		ps.setString(1,id);
		//값을 읽어오기 위해 resultset을 불러온다.
		ResultSet rs=ps.executeQuery();
		//설정하기 위해 첫번째로이동시키고
		
		//값을 읽어오기 위해 데이터질의문이 저장된 rs안의 id값,name값,password값을 getString으로 불러온다.
		User user=null;
		if(rs.next()) {
		user=new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		
		}
		
		
		//옮기고 닫고 리턴값을 반환한다.
		rs.close();
		ps.close();
		c.close();
		if(user == null) throw new EmptyResultDataAccessException(1);
		return user;
		
	}
	
	public void deleteAll()throws SQLException,ClassNotFoundException{
	//데이터베이스에 연결한다.
		//빈 초기값을 먼저 설정해서 제대로 값에 들어오지 않았을시에 close를 실행하지않는다.
		//예외처리
		Connection c=null;
		PreparedStatement ps=null;
		try {
		c=dataSource.getConnection();
		//ps에 "Delete from users"를 입력해서 삭제질의를 넘긴다.
		ps=c.prepareStatement("delete from users");
		// 데이터베이스를 업데이트한다.
		ps.executeUpdate();
		}catch(SQLException e) {
			throw e;
		}finally {
			if(ps!=null) {
				try {
					ps.close();
					
				}catch(SQLException e)
				{
					
				}
			}
			if(c !=null) {
				try {
					c.close();
					
				}catch(SQLException e)
				{
					
				}
			}
		}
	}
	
	
	
	public int getCount()throws SQLException{
		//데이터베이스를 연결한다.
		Connection c=dataSource.getConnection();
		//PreparedStatement에 쿼리문을 작성한다.
		PreparedStatement ps=c.prepareStatement("select count(*) from users");
		//읽어오기 위해서 RsultSet에 데이터베이스 내용을 넣는다.
		ResultSet rs=ps.executeQuery();
		rs.next();
		int count=rs.getInt(1);
		
		return count;
		
	}
	
	
		
	

	//같은 내용을 하나로 합쳐서 코드를 줄이기 위한 방법이다.
	//getConnection()메소드를 Connection으로 반환해서 각각의 필요한 메소드에 반환한다.
	//이경우엔 커넥션을 연결하기 위해 반환한다.

	//Connection은 서버와 연결하는역할을한다
	//PreparedStatement는 데이터베이스에 명령문을 집어넣는다
	//ps.setString은 정보를 입력한다
//end of main
	
	
	
}//end of class
