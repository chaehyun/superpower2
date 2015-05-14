package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Ownership;

/**
 * DB�κ��� ���� ���ڵ带 ������. FK�� ȸ��ID�� �����ڵ带 �Ķ���ͷ� ���
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetOwnership {

	/**
	 * @param id
	 *            foreign key
	 * @param cCode
	 *            foreign key
	 * @return ���� ��ü or null
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static Ownership doAction(String id, String cCode)
			throws SQLException {

		// DTO
		Ownership ownership = null;

		// ���� ����
		String sql = "select * from ownership where id=? and c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, cCode);
		ResultSet resultSet = pstmt.executeQuery();

		// ��� ���ڵ带 ��ü�� ����
		if (resultSet.next()) {
			ownership = new Ownership();
			ownership.setId(id);
			ownership.setC_code(cCode);
			ownership.setUsed(resultSet.getString("used"));
		}

		return ownership;
	}
}
