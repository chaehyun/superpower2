package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Item;

public class GetAllItems {
	synchronized public static List<Item> doAction() throws SQLException {

		// 쿼리 실행
		String sql = "select * from item";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// 결과들을 ArrayList에 저장
		List<Item> itemList = new ArrayList<Item>();
		
		while (resultSet.next()) {
			Item item = new Item();
			item.seti_code(resultSet.getString("i_code"));
			item.setmajor(resultSet.getString("major"));
			item.setmiddle(resultSet.getString("middle"));
			item.setminor(resultSet.getString("minor"));
			item.setsales_volume(resultSet.getInt("sales_volume"));
			item.settotal_stock(resultSet.getInt("total_stock"));
			item.setprice(resultSet.getInt("price"));
			item.setimage(resultSet.getString("image"));

			itemList.add(item);
		}

		return itemList;
	}
}
