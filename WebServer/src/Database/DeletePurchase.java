package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 구매 하나 삭제. PK인 구매코드를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class DeletePurchase {

	/**
	 * @param code
	 *            primary key
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	synchronized public static void doAction(String code) throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "delete from purchase where p_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
