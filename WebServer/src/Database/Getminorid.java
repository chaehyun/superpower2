package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Getminorid {

public static String getminor(String i_code) throws SQLException{
		
		String minor = null;
		
		String sql = "select minor from item where i_code = ?";
		
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, i_code);
		pstmt.executeQuery();
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()){
			minor = rs.getString("minor");
		}
		
		return minor;
	}
}
