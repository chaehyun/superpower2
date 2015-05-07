package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Elements.Ownership;

public class GetPersonalCoupon {
	
	synchronized public static List<Ownership> doAction(String userid) {
		
		List<Ownership> personalownershiplist = new ArrayList<Ownership>();
		try {
			String query = "select * from ownership where id = ?";
			PreparedStatement pstmt = DbConnector.getInstance().getConnection()
					.prepareStatement(query);

			pstmt.setString(1, userid);

			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Ownership ownership = new Ownership();
				ownership.setId("id");
				ownership.setC_code(rs.getString("c_code"));
				ownership.setUsed(rs.getString("used"));
				
				personalownershiplist.add(ownership);
			}
		} catch (Exception e) {
			System.err.println("sql error = " + e.getMessage());
		}

		return personalownershiplist;
	}
}
