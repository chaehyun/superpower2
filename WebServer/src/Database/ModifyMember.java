package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Member;

/**
 * DB에서 회원 하나 수정. PK인 기존 회원 ID와 변경사항인 회원 객체를 파라미터로 사용.
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class ModifyMember {

	/**
	 * @param originId
	 *            primary key
	 * @param member
	 *            변경 데이터
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static void doAction(String originId, Member member)
			throws SQLException {
		
		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "update member set id=?, name=?, password=?, sex=?, age=?, favorite=?, enter_count=? where id=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, member.getId());
		pstmt.setString(2, member.getName());
		pstmt.setString(3, member.getPassword());
		pstmt.setString(4, Character.toString(member.getSex()));
		pstmt.setInt(5, member.getAge());
		pstmt.setString(6, member.getFavorite());
		pstmt.setInt(7, member.getEnterCount());
		pstmt.setString(8, originId);
		pstmt.executeUpdate();
		
		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
