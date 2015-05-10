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
	 */
	synchronized public static void doAction(String id) throws SQLException {

		// 쿼리 실행
		String sql = "delete from member where id=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
	}
}
