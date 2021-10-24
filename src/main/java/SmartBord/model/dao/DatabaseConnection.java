package SmartBord.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbctest";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL,"root","1234567890q");
	}
}