package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Item;

/**
 * ��ǰ ���̺��� ��� ���ڵ带 ����. ���ΰ�ħ��
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetAllItems {

	/**
	 * @return ��ǰ ����Ʈ
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static List<Item> doAction() throws SQLException {

		// ���� ����
		String sql = "select * from item";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// ������� ArrayList�� ����
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
