package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Beacon;

/**
 * DB���� ���� �ϳ� ����. PK�� ���� MAC�ּҿ� ��������� ���� ��ü�� �Ķ���ͷ� ���.
 * 
 * @author Minji, Seongjun
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class ModifyBeacon {

	/**
	 * @param originMacAddr
	 *            primary key
	 * @param beacon
	 *            ���� ������
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static void doAction(String originMacAddr, Beacon beacon)
			throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "update beacon set mac_addr=?, location=? where mac_addr=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, beacon.getMacAddr());
		pstmt.setString(2, beacon.getLocation());
		pstmt.setString(3, originMacAddr);
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
