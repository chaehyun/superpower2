package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Purchase;

/**
 * DB�κ��� ���� ���ڵ带 ������. PK�� �����ڵ带 �Ķ���ͷ� ���
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetPurchase {

	/**
	 * @param code
	 *            primary key
	 * @return ���� ��ü or null
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	synchronized public static Purchase doAction(String code)
			throws SQLException {

		// DTO
		Purchase purchase = null;

		// ���� ����
		String sql = "select * from purchase where p_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		ResultSet resultSet = pstmt.executeQuery();

		// ��� ���ڵ带 ��ü�� ����
		if (resultSet.next()) {
			purchase = new Purchase();
			purchase.setP_code(code);
			purchase.setId(resultSet.getString("id"));
			purchase.setI_code(resultSet.getString("i_code"));
			purchase.setCount(resultSet.getInt("count"));
			purchase.setPur_date(resultSet.getDate("pur_date"));
		}

		return purchase;
	}
}
