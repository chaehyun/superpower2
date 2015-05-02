package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {

	private String url = "jdbc:mysql://localhost:3306/super_power";
	private String user = "root";
	private String password = "root";
	private Connection connection;

	// ������ - DB�� ������.
	public DbConnector() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);

			System.out.println("Success - Connect MySQL");
		} catch (Exception e) {
			System.out.println("Failed - Connect MySQL");
		}
	}
}
