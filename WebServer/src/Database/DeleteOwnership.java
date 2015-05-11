package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 소유 하나 삭제. FK인 회원ID, 쿠폰코드를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class DeleteOwnership {

	/**
	 * @param id
	 *            foreign key
	 * @param cCode
	 *            foreign key
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	synchronized public static void doAction(String id, String cCode)
			throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "delete from ownership where id=? and c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, cCode);
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
