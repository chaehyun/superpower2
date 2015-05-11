package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Ownership;

/**
 * DB에서 소유 하나 수정. FK인 기존회원ID, 기존쿠폰코드와 변경사항인 소유 객체를 파라미터로 사용.
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class ModifyOwnership {

	/**
	 * @param originId
	 *            foreign key
	 * @param originCCode
	 *            foreign key
	 * @param ownership
	 *            변경 데이터
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	synchronized public static void doAction(String originId,
			String originCCode, Ownership ownership) throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "update ownership set id=?, c_code=?, used=? where id=? and c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, ownership.getId());
		pstmt.setString(2, ownership.getC_code());
		pstmt.setString(3, ownership.getUsed());
		pstmt.setString(4, originId);
		pstmt.setString(5, originCCode);
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
