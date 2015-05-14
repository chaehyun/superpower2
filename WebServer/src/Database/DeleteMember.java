package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 회원 하나 삭제. PK인 회원ID를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class DeleteMember {

	/**
	 * @param id
	 *            primary key
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static void doAction(String id) throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "delete from member where id=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
