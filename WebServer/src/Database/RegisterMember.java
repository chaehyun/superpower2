package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterMember {
	
	public static void doAction(JSONObject register) throws SQLException, JSONException {
		String sql = null;
		sql = "insert into member value(?, ?, ?, ?, ?, ? ,?)";

		DbConnector.getInstance().getConnection().setAutoCommit(false);
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		
		pstmt.setString(1, register.getString("ID"));
		pstmt.setString(2, register.getString("Name"));
		pstmt.setString(3, register.getString("Password"));
		pstmt.setString(4, register.getString("Sex"));
		pstmt.setInt(5, register.getInt("Age"));
		pstmt.setString(6, register.getString("Favorite"));
		pstmt.setInt(7, 0);

		pstmt.executeUpdate();

		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
	
}
