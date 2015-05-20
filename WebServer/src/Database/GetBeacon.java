package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Beacon;

/**
 * DB�κ��� ���� ���ڵ带 ������. PK�� MAC�ּҸ� �Ķ���ͷ� ���
 * 
 * @author Minji, Seongjun
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class GetBeacon {

	/**
	 * @param macAddr
	 *            primary key
	 * @return ���� ��ü or null
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static Beacon doAction(String macAddr) throws SQLException {

		// DTO
		Beacon beacon = null;

		// ���� ����
		String sql = "select * from beacon where mac_addr=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, macAddr);
		ResultSet resultSet = pstmt.executeQuery();

		// ��� ���ڵ带 ��ü�� ����
		if (resultSet.next()) {
			beacon = new Beacon();
			beacon.setMacAddr(resultSet.getString("mac_addr"));
			beacon.setLocation(resultSet.getString("location"));
		}

		return beacon;
	}
}
