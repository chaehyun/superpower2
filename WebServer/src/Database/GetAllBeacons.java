package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Beacon;

/**
 * 비콘 테이블의 모든 레코드를 받음. 새로고침용
 * 
 * @author Seongjun, Minji
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class GetAllBeacons {

	/**
	 * @return 비콘 리스트
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static List<Beacon> doAction() throws SQLException {

		// 쿼리 실행
		String sql = "select * from beacon";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// 결과들을 ArrayList에 저장
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
