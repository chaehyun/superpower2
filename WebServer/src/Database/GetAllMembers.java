package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Member;

/**
 * ȸ�� ���̺��� ��� ���ڵ带 ����. ���ΰ�ħ��
 * 
 * @author Seongjun
 * @since 2015/5/4
 * @version 2015/5/4
 */
public class GetAllMembers {
	synchronized public static List<Member> doAction() throws SQLException {

		// ���� ����
		String sql = "select * from member";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// ������� ArrayList�� ����
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
