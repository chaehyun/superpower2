package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ���� �ϳ� ����. FK�� ȸ��ID, �����ڵ带 �Ķ���ͷ� ���
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class DeleteOwnership {

	/**
	 * @param id
	 *            foreign key
	 * @param cCode
	 *            foreign key
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	synchronized public static void doAction(String id, String cCode)
			throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "delete from ownership where id=? and c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, cCode);
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
