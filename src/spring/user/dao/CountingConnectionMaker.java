package spring.user.dao;
import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
	int count = 0;
	private ConnectionMaker realConnectionMaker;
	
	CountingConnectionMaker(ConnectionMaker realConnectionMaker)
	{
		this.realConnectionMaker=realConnectionMaker;
	}
	
	public Connection makeConnection()throws SQLException,ClassNotFoundException {
		
		this.count++;
		return realConnectionMaker.makeConnection();
	}
	
	public int getCount() {
		
		return this.count;
	}
	
	
	
}
