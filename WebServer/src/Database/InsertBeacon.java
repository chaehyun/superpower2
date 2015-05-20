package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Beacon;

/**
 * DB���� ���� �ϳ� �߰�. ���� ��ü�� �Ķ���ͷ� ���
 * 
 * @author Seongjun, Minji
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class InsertBeacon {

	/**
	 * @param beacon
	 *            ������ ��ü
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static void doAction(Beacon beacon) throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "insert into beacon value(?, ?)";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, beacon.getMacAddr());
		pstmt.setString(2, beacon.getLocation());
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
