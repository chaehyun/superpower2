package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 상품 하나 삭제. PK인 상품코드를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class DeleteItem {

	/**
	 * @param code
	 *            primary key
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static void doAction(String code) throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "delete from item where i_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
