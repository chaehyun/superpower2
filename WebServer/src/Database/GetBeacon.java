package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Beacon;

/**
 * DB로부터 비콘 레코드를 가져옴. PK인 MAC주소를 파라미터로 사용
 * 
 * @author Minji, Seongjun
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class GetBeacon {

	/**
	 * @param macAddr
	 *            primary key
	 * @return 비콘 객체 or null
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static Beacon doAction(String macAddr) throws SQLException {

		// DTO
		Beacon beacon = null;

		// 쿼리 실행
		String sql = "select * from beacon where mac_addr=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, macAddr);
		ResultSet resultSet = pstmt.executeQuery();

		// 결과 레코드를 객체에 저장
		if (resultSet.next()) {
			beacon = new Beacon();
			beacon.setMacAddr(resultSet.getString("mac_addr"));
			beacon.setLocation(resultSet.getString("location"));
		}

		return beacon;
	}
}
