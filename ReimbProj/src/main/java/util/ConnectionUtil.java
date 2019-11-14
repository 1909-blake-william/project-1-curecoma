package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		String url = System.getenv("REIMB_URL");
		String username = System.getenv("REIMB_UN");
		String password = System.getenv("REIMB_PW");
		return DriverManager.getConnection(url, username, password);
	}
}
