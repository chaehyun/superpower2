package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Purchase;

public class GetAllPurchase {
	synchronized public static List<Purchase> doAction() throws SQLException {

		// ���� ����
		String sql = "select * from purchase";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// ������� ArrayList�� ����
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		
		while (resultSet.next()) {
			Purchase purchase = new Purchase();
			purchase.setId(resultSet.getString("id"));
			purchase.setI_code(resultSet.getString("i_code"));
			purchase.setCount(resultSet.getInt("count"));
			purchase.setPur_date(resultSet.getDate("pur_date"));
			
			purchaseList.add(purchase);
		}

		return purchaseList;
	}
}
