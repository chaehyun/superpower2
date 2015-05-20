package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetLocationFromBeacon {

	public static String doAction(String mac_addr) throws SQLException {
		
		// 위치 정보 보관
		String location = null;

		// 쿼리 실행 
		String query = "select location from Beacon where mac_addr=?";
		
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(query);
		pstmt.setString(1, mac_addr);
		ResultSet rs = pstmt.executeQuery();
		
		
		while (rs.next()) {
			location = rs.getString("location");
		}

		return location;
	}


}
