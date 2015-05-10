package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Member;

/**
 * DB로부터 회원 레코드를 가져옴.
 * PK인 회원ID를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class GetMember {

	/**
	 * @param id
	 *            primary key
	 * @return 회원 객체 or null
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	synchronized public static Member doAction(String id) throws SQLException{
		
		// DTO
		Member member = null;
		
		// 쿼리 실행
		String sql = "select * from member where id=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet resultSet = pstmt.executeQuery();

		// 결과 레코드를 객체에 저장
		if (resultSet.next()) {
			member = new Member();
			member.setId(id);
			member.setName(resultSet.getString("name"));
			member.setPassword(resultSet.getString("password"));
			member.setSex(resultSet.getString("sex").charAt(0));
			member.setAge(resultSet.getInt("age"));
			member.setFavorite(resultSet.getString("favorite"));
			member.setEnterCount(resultSet.getInt("enter_count"));
			member.setLogFlag("t".equals(resultSet.getString("logflag")));
		}

		return member;
	}
}
