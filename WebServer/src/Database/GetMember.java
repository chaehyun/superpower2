package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Member;

/**
 * DB�κ��� ȸ�� ���ڵ带 ������.
 * PK�� ȸ��ID�� �Ķ���ͷ� ���
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class GetMember {

	/**
	 * @param id
	 *            primary key
	 * @return ȸ�� ��ü or null
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	synchronized public static Member doAction(String id) throws SQLException{
		
		// DTO
		Member member = null;
		
		// ���� ����
		String sql = "select * from member where id=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet resultSet = pstmt.executeQuery();

		// ��� ���ڵ带 ��ü�� ����
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
