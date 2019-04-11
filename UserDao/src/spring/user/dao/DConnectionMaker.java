package spring.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DConnectionMaker implements ConnectionMaker {

	public Connection makeConnection()throws SQLException,ClassNotFoundException{

Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection("jdbc:mysql//localhost/3306","root","1234");

return c;
		
	}
}
