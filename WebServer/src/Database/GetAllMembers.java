package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Member;

/**
 * 회원 테이블의 모든 레코드를 받음. 새로고침용
 * 
 * @author Seongjun
 * @since 2015/5/4
 * @version 2015/5/4
 */
public class GetAllMembers {
	synchronized public static List<Member> doAction() throws SQLException {

		// 쿼리 실행
		String sql = "select * from member";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// 결과들을 ArrayList에 저장
		List<Member> memberList = new ArrayList<Member>();
		
		while (resultSet.next()) {
			Member member = new Member();
			member.setId(resultSet.getString("id"));
			member.setPassword(resultSet.getString("password"));
			member.setName(resultSet.getString("name"));
			member.setSex(resultSet.getString("sex").charAt(0));
			member.setAge(resultSet.getInt("age"));
			member.setFavorite(resultSet.getString("favorite"));
			member.setEnterCount(resultSet.getInt("enter_count"));
			member.setLogFlag("t".equals(resultSet.getString("logflag")));

			memberList.add(member);
		}

		return memberList;
	}
}
