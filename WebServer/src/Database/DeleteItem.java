package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ��ǰ �ϳ� ����. PK�� ��ǰ�ڵ带 �Ķ���ͷ� ���
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class DeleteItem {

	/**
	 * @param code
	 *            primary key
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static void doAction(String code) throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "delete from item where i_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
