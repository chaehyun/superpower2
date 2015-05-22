package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetLocationFromBeacon {

	public static String doAction(String mac_addr) throws SQLException {
		
		// ��ġ ���� ����
		String location = null;

		// ���� ���� 
		String query = "select location from beacon where mac_addr=?";
		
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(query);
		pstmt.setString(1, mac_addr);
		ResultSet rs = pstmt.executeQuery();
		
		
		while (rs.next()) {
			location = rs.getString("location");
		}

		System.out.println(location);
		return location;
	}


}
