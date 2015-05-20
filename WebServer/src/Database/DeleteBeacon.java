package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 비콘 하나 삭제. PK인 MAC주소를 파라미터로 사용
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
	 *             쿼리 실행 에러시 발생
	 */
	public static void doAction(String macAddr) throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "delete from beacon where mac_addr=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, macAddr);
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}