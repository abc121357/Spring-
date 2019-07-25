package spring.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;
import javax.sql.DataSource;
public class UserDaoImpl implements UserDao {
    //����̹� ���� ��������� �����.
    //DI�޾Ƽ� �� �� �ְ� �����.
    private DataSource dataSource;
    private ConnectionMaker connectionMaker;
    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper =
            new RowMapper<User>(){
                public User mapRow(ResultSet rs, int rowNum) throws SQLException{
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            };

    UserDaoImpl(){
        //�������� ����
        //this.connection=connection
        //DaoFactoryŬ�����̿�
        //DaoFactory daoFactory=new DaoFactory();
        //this.connection=DaoFactory.ConnectionMaker();
        //�������� �˻�
		/*ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		this.connectionMaker=context.getBean("connectionMaker",ConnectionMaker.class);*/
    }

    //���ϴ� ����̹��� �����ϱ����� �Ű������� �����´�.
    //������ ���� ���� template�� jdbcContext ���� �����ϴ� ������Ƽ
	/*public void setDataSource(DataSource dataSource){
		this.jdbcContext=new JdbcContext();

		this.jdbcContext.setDataSource(dataSource);

		this.dataSource=dataSource;
	}*/

    //���������� �ϴ� jdbcTemplate
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
        this.dataSource=dataSource;

    }



    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker=connectionMaker;
    }

    //�����ͺ��̽��� �ҷ��ͼ� ���� �Է��ϴ� add �޼ҵ�
    public void add(final User user)throws DuplicateUserIdException{
        //Ŭ������ ��������
        //����(����) Ŭ������ ����� �޼ҵ�ȿ� �ִ� ����� �ִ�.
        //Ư�� �޼ҵ忡�� ����ϱ⿡ ����. Ŭ������ �����Ҷ� public Ű����� ���� �ʴ´�.
        try {
            Connection c = dataSource.getConnection();
            System.out.println("�� ����");
            //�����ͺ��̽��� ���ǹ��� �Է�

            //���� StatementStrategy�������̽��� AddStatementŬ�������� ������ ��� ���� �ִ� �۾��� �Ѵ�.
            //���� ���� ���ø��� ����ؼ� ���� ���
		/*
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c)throws SQLException {
				PreparedStatement ps=c.prepareStatement("INSERT INTO USERS(ID,NAME,PASSWORD) VALUES(?,?,?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());

				System.out.println(" user���̵� Ȯ�� :" +user.getId());
				System.out.println(" user���̵� Ȯ�� :" +user.getName());
				System.out.println(" user���̵� Ȯ�� :" +user.getPassword());
				System.out.println("ps������ �����Ϸ�");
				return ps;
			}
		});*/

            //Spring ���ø����� ª�� ���� �Լ�
            this.jdbcTemplate.update("INSERT INTO USERS VALUES(?,?,?)"
                    , user.getId()
                    , user.getName()
                    , user.getPassword());

            System.out.println("Ŀ�ؼ�,���� �ݱ�Ϸ�");
            //preparedStatementŬ���� ��ü�� ps�� user id,name,password������ �Է��Ѵ�.
            //�Է��� Ŀ���� �ϱ� ���� ������ ������Ʈ�Ѵ�.

        }catch(SQLException e)
        {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY)
                throw new DuplicateUserIdException(e);
            else
                throw new RuntimeException(e);
        }
    }
    public User get(String id){
//		�Ϲ� �޼ҵ�� �ۼ��ϴ� �ڵ�
// 		//Ŀ�ؼ� c�� UserDaoTest���� �ҷ����� connectinMaker���� makeConnection�� �ҷ��´�.
//		Connection c=dataSource.getConnection();
//		//c�����ͺ��̽� ���ǹ��� rs�� �Է��Ѵ�.
//		PreparedStatement ps=c.prepareStatement("Select * from users where id=?");
//		//?���� �����Ѵ�.
//		ps.setString(1,id);
//		//���� �о���� ���� resultset�� �ҷ��´�.
//		ResultSet rs=ps.executeQuery();
//		//�����ϱ� ���� ù��°���̵���Ű��
//
//		//���� �о���� ���� ���������ǹ��� ����� rs���� id��,name��,password���� getString���� �ҷ��´�.
//		User user=null;
//		if(rs.next()) {
//		user=new User();
//		user.setId(rs.getString("id"));
//		user.setName(rs.getString("name"));
//		user.setPassword(rs.getString("password"));
//
//		}
//
//
//		//�ű�� �ݰ� ���ϰ��� ��ȯ�Ѵ�.
//		rs.close();
//		ps.close();
//		c.close();
//		if(user == null) throw new EmptyResultDataAccessException(1);
//		return user;

        //������ ���ø����� �ҷ��� �ڵ�
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                this.userMapper);
    }

    public void deleteAll(){
        //�����ͺ��̽��� �����Ѵ�.
        //�� �ʱⰪ�� ���� �����ؼ� ����� ���� ������ �ʾ����ÿ� close�� ���������ʴ´�.
        //����ó��

        //���� jdbcContextWithStatementStrategy�� ������ �ѱ�� �ݱ⸦ �� �Ѵ�.

        //jdbcContext ���ø����� ���� executeSql ���� ó�� -1
        //this.jdbcContext.executeSql("delete from users");

        //���������� ������ ���ø� -2
		/*this.jdbcTemplate.update(
				//update�� PreparedStatementCreator�� ��ü�� ����� sql���� �ִ´�.
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						return connection.prepareStatement("delete from users");
					}
				}

		);*/

        try {
            this.jdbcTemplate.update("delete from users");
        }catch(Exception e){
            e.getMessage();
        }

    }



    public int getCount() {
//		Connection c=null;
//		PreparedStatement ps=null;
//		ResultSet rs=null;
//
//		//�����ͺ��̽��� �����Ѵ�.
//		try{
//			c=dataSource.getConnection();
//
//		//PreparedStatement�� �������� �ۼ��Ѵ�.
//		ps=c.prepareStatement("select count(*) from users");
//		//�о���� ���ؼ� RsultSet�� �����ͺ��̽� ������ �ִ´�.
//		rs=ps.executeQuery();
//		rs.next();
//		return rs.getInt(1);
//		}catch(SQLException e){
//			throw e;
//		}finally {
//			if(rs!=null) {
//				try {
//					rs.close();
//				}catch(SQLException e){
//					throw e;
//				}
//			}
//			if(ps!=null) {
//				try {
//					ps.close();
//				}catch(SQLException e) {
//					throw e;
//				}
//			}
//			if(c !=null) {
//				try {
//					c.close();
//				}catch(SQLException e) {
//					throw e;
//					}
//			}
//				}
        //���������� �ۼ��� 2���� �ݹ��� ����� PreparedStatement�� ResultSet�� ó��
        return this.jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement("SELECT count(*) FROM USERS");
            }
        }, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                resultSet.next();
                return resultSet.getInt(1);
            }
        });
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException{
        //�����ͺ��̽��� �����Ѵ�.
        //�� �ʱⰪ�� ���� �����ؼ� ����� ���� ������ �ʾ����ÿ� close�� ���������ʴ´�.
        //����ó��
        Connection c=null;
        PreparedStatement ps = null;

        try {
            //������ �ҽ��� xml ���������� ����ϰ� ������ �Ѵ�����
            c=dataSource.getConnection();
            //������ Ŀ�ؼ��� �Լ��� ������ ������ ��´�.
            ps=stmt.makePreparedStatement(c);

            System.out.println("������ ��ϿϷ�"+ps);
            //�������� �����Ѵ�.
            ps.executeUpdate();

            System.out.println("������ ������Ʈ�Ϸ�");
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



    public List<User> getAll(){


        return this.jdbcTemplate.query("select * from users order by id",
                this.userMapper);
    }

    //���� ������ �ϳ��� ���ļ� �ڵ带 ���̱� ���� ����̴�.
    //getConnection()�޼ҵ带 Connection���� ��ȯ�ؼ� ������ �ʿ��� �޼ҵ忡 ��ȯ�Ѵ�.
    //�̰�쿣 Ŀ�ؼ��� �����ϱ� ���� ��ȯ�Ѵ�.

    //Connection�� ������ �����ϴ¿������Ѵ�
    //PreparedStatement�� �����ͺ��̽��� ��ɹ��� ����ִ´�
    //ps.setString�� ?�� ���� ������ �Է��Ѵ�
//end of main



}//end of class
