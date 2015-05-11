package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Member;

/**
 * DB에서 회원 하나 추가. 회원 객체를 파라미터로 사용
 * 
 * @author Seongjun, Minji
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class InsertMember {

	/**
	 * @param member
	 *            데이터 객체
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	synchronized public static void doAction(Member member) throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);
		
		// 쿼리 실행
		String sql = "insert into member value(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, member.getId());
		pstmt.setString(2, member.getName());
		pstmt.setString(3, member.getPassword());
		pstmt.setString(4, Character.toString(member.getSex()));
		pstmt.setInt(5, member.getAge());
		pstmt.setString(6, member.getFavorite());
		pstmt.setInt(7, member.getEnterCount());
		pstmt.setString(8, member.getLogFlag() ? "t" : "f");
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
