package spring.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy {

	public PreparedStatement makeStatement(Connection c)throws SQLException{
		PreparedStatement ps=c.prepareStatement("delete from users");
		return ps;
	}
}
