package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Beacon;

/**
 * ���� ���̺��� ��� ���ڵ带 ����. ���ΰ�ħ��
 * 
 * @author Seongjun, Minji
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class GetAllBeacons {

	/**
	 * @return ���� ����Ʈ
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static List<Beacon> doAction() throws SQLException {

		// ���� ����
		String sql = "select * from beacon";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// ������� ArrayList�� ����
		List<Beacon> beaconList = new ArrayList<Beacon>();

		while (resultSet.next()) {
			Beacon beacon = new Beacon();
			beacon.setMacAddr(resultSet.getString("mac_addr"));
			beacon.setLocation(resultSet.getString("location"));

			beaconList.add(beacon);
		}

		return beaconList;
	}
}
