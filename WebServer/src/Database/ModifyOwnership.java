package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Ownership;

/**
 * DB���� ���� �ϳ� ����. FK�� ����ȸ��ID, ���������ڵ�� ��������� ���� ��ü�� �Ķ���ͷ� ���.
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class ModifyOwnership {

	/**
	 * @param originId
	 *            foreign key
	 * @param originCCode
	 *            foreign key
	 * @param ownership
	 *            ���� ������
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	synchronized public static void doAction(String originId,
			String originCCode, Ownership ownership) throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "update ownership set id=?, c_code=?, used=? where id=? and c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, ownership.getId());
		pstmt.setString(2, ownership.getC_code());
		pstmt.setString(3, ownership.getUsed());
		pstmt.setString(4, originId);
		pstmt.setString(5, originCCode);
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
