package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
	
	// for singletone
	private static DbConnector instance = null;

	private String url = "jdbc:mysql://localhost:3306/super_power";
	private String user = "root";
	private String password = "root";
	private Connection connection;

	// �̱��� ��ü ��ȯ
	public static DbConnector getInstance() {
		if(instance == null)
			instance = new DbConnector();
		
		return instance;
	}
	
	// ������ - DB�� ������.
	private DbConnector() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(url, user, password);

			System.out.println("Success - Connect MySQL");
		} catch (Exception e) {
			System.out.println("Failed - Connect MySQL");
		}
	}
	
	public void closeConnection() {
		try {
			connection.close();
			
			System.out.println("Success - Disconnect MySQL");
		} catch(SQLException e) {
			System.out.println("Failed - Disconnect MySQL");
		}
	}
	
	// connection ��ȯ
	public Connection getConnection() {
		return this.connection;
	}
}
