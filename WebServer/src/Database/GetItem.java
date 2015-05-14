package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Item;

/**
 * DB�κ��� ��ǰ ���ڵ带 ������. PK�� ��ǰ�ڵ带 �Ķ���ͷ� ���
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetItem {

	/**
	 * @param code
	 *            primary key
	 * @return ��ǰ ��ü or null
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static Item doAction(String code) throws SQLException {

		// DTO
		Item item = null;

		// ���� ����
		String sql = "select * from item where i_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		ResultSet resultSet = pstmt.executeQuery();

		// ��� ���ڵ带 ��ü�� ����
		if (resultSet.next()) {
			item = new Item();
			item.seti_code(code);
			item.setmajor(resultSet.getString("major"));
			item.setmiddle(resultSet.getString("middle"));
			item.setminor(resultSet.getString("minor"));
			item.setsales_volume(resultSet.getInt("sales_volume"));
			item.settotal_stock(resultSet.getInt("total_stock"));
			item.setprice(resultSet.getInt("price"));
			item.setimage(resultSet.getString("image"));
		}

		return item;
	}
}
