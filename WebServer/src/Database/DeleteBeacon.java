package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ���� �ϳ� ����. PK�� MAC�ּҸ� �Ķ���ͷ� ���
 * 
 * @author Minji, Seongjun
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class DeleteBeacon {

	/**
	 * @param macAddr
	 *            primary key
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static void doAction(String macAddr) throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "delete from beacon where mac_addr=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, macAddr);
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}