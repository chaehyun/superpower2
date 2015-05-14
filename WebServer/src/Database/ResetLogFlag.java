package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 모든 계정의 로그인여부를 초기화함
 * 
 * @author Seongjun, Minji
 *
 */
public class ResetLogFlag {

	/**
	 * 로그인 여부 모두 초기화. 프로그램 시작시 호출됨.
	 */
	public static void doAction() throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "update member set logflag=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, "f");
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
