package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Beacon;

/**
 * DB에서 비콘 하나 수정. PK인 기존 MAC주소와 변경사항인 비콘 객체를 파라미터로 사용.
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
	 *            변경 데이터
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static void doAction(String originMacAddr, Beacon beacon)
			throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "update beacon set mac_addr=?, location=? where mac_addr=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, beacon.getMacAddr());
		pstmt.setString(2, beacon.getLocation());
		pstmt.setString(3, originMacAddr);
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
