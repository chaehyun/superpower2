package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Elements.Purchase;

public class GetPersonalItem {
	public static List<Purchase> doAction(String userid) {

		List<Purchase> personalpurchaselist = new ArrayList<Purchase>();
		try {
			String query = "select * from purchase where id = ?";
			PreparedStatement pstmt = DbConnector.getInstance().getConnection()
					.prepareStatement(query);

			pstmt.setString(1, userid);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setId(rs.getString("id"));
				purchase.setI_code(rs.getString("i_code"));
				purchase.setCount(rs.getInt("count"));
				purchase.setPur_date(rs.getDate("pur_date"));

				personalpurchaselist.add(purchase);
			}
		} catch (Exception e) {
			System.err.println("sql error = " + e.getMessage());
		}

		return personalpurchaselist;
	}

}
