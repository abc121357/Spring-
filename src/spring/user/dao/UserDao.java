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
	public void add(final User user)throws ClassNotFoundException,SQLException{
		//클래스가 많아지면
		//로컬(내부) 클래스를 만들어 메소드안에 넣는 방법이 있다.
		//특정 메소드에서 사용하기에 좋다. 클래스를 정의할때 public 키워드는 적지 않는다.
		
		Connection c=dataSource.getConnection();
		System.out.println("값 들어옴");
		//데이터베이스에 질의문을 입력
		//이제 StatementStrategy인터페이스의 AddStatement클래스에서 쿼리를 담고 값을 넣는 작업을 한다.
		StatementStrategy stmt=new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c)throws SQLException {
				PreparedStatement ps=c.prepareStatement("INSERT INTO USERS(ID,NAME,PASSWORD) VALUES(?,?,?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());

				System.out.println(" user아이디 확인 :" +user.getId());
				System.out.println(" user아이디 확인 :" +user.getName());
				System.out.println(" user아이디 확인 :" +user.getPassword());
				System.out.println("ps쿼리문 설정완료");
				return ps;
			}
		};
		jdbcContextWithStatementStrategy(stmt);
		System.out.println("커넥션,쿼리 닫기완료");
		//preparedStatement클래스 객체인 ps에 user id,name,password정보를 입력한다.
		//입력후 커밋을 하기 위해 쿼리를 업데이트한다.
		
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
	
	public void deleteAll()throws SQLException{
	//데이터베이스에 연결한다.
		//빈 초기값을 먼저 설정해서 제대로 값에 들어오지 않았을시에 close를 실행하지않는다.
		//예외처리
		
		//이제 jdbcContextWithStatementStrategy가 쿼리문 넘기기 닫기를 다 한다.
		jdbcContextWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c)throws SQLException{
				return c.prepareStatement("delete from users");
			}
		});
		
	}
	
	
	
	public int getCount()throws SQLException{
		Connection c=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		//데이터베이스를 연결한다.
		try{
			c=dataSource.getConnection();
		
		//PreparedStatement에 쿼리문을 작성한다.
		ps=c.prepareStatement("select count(*) from users");
		//읽어오기 위해서 RsultSet에 데이터베이스 내용을 넣는다.
		rs=ps.executeQuery();
		rs.next();
		return rs.getInt(1);
		}catch(SQLException e){
			throw e;
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				}catch(SQLException e){
					throw e;
				}
			}
			if(ps!=null) {
				try {
					ps.close();
				}catch(SQLException e) {
					throw e;
				}
			}
			if(c !=null) {
				try {
					c.close();
				}catch(SQLException e) {
					throw e;
					}
			}
				}
		}
	
	
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException{
		//데이터베이스에 연결한다.
				//빈 초기값을 먼저 설정해서 제대로 값에 들어오지 않았을시에 close를 실행하지않는다.
				//예외처리
		Connection c=null;
		PreparedStatement ps = null;
		
		try {
			//데이터 소스에 xml 설정파일을 등록하고 연결을 한다음에
			c=dataSource.getConnection();
			//연결한 커넥션을 함수로 빼내서 쿼리를 담는다.
			ps=stmt.makePreparedStatement(c);

			System.out.println("쿼리문 등록완료"+ps);
			//쿼리문을 실행한다.
			ps.executeUpdate();

			System.out.println("쿼리문 업데이트완료");
		}catch(SQLException e) {
			throw e;
		} finally {
			if(ps != null) {
				try {
					ps.close();
				}catch(SQLException e) {
					
				}
			}
			if(c != null) {
				try {
					c.close();
				}catch(SQLException e) {
					
				}
			}
		}
		
		
	}
	

	//같은 내용을 하나로 합쳐서 코드를 줄이기 위한 방법이다.
	//getConnection()메소드를 Connection으로 반환해서 각각의 필요한 메소드에 반환한다.
	//이경우엔 커넥션을 연결하기 위해 반환한다.

	//Connection은 서버와 연결하는역할을한다
	//PreparedStatement는 데이터베이스에 명령문을 집어넣는다
	//ps.setString은 정보를 입력한다
//end of main
	
	
	
}//end of class
