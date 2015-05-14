package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ȸ�� �ϳ� ����. PK�� ȸ��ID�� �Ķ���ͷ� ���
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class DeleteMember {

	/**
	 * @param id
	 *            primary key
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static void doAction(String id) throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "delete from member where id=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
