package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 쿠폰 하나 삭제. PK인 쿠폰코드를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class DeleteCoupon {

	/**
	 * @param code
	 *            primary key
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	synchronized public static void doAction(String code) throws SQLException {

		// 쿼리 실행
		String sql = "delete from coupon where c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		pstmt.executeUpdate();
		
		// 커밋
		DbConnector.getInstance().getConnection().commit();
	}
}
