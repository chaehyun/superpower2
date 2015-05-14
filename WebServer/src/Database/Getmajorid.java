package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Getmajorid {
	
	public static String getmajor(String i_code) throws SQLException{
		
		String major = null;
		
		String sql = "select major from item where i_code = ?";
		
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, i_code);
		pstmt.executeQuery();
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()){
			major = rs.getString("major");
		}
		
		return major;
	}
}
