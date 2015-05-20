package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Beacon;

/**
 * DB에서 비콘 하나 추가. 비콘 객체를 파라미터로 사용
 * 
 * @author Seongjun, Minji
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class InsertBeacon {

	/**
	 * @param beacon
	 *            데이터 객체
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static void doAction(Beacon beacon) throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "insert into beacon value(?, ?)";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, beacon.getMacAddr());
		pstmt.setString(2, beacon.getLocation());
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
