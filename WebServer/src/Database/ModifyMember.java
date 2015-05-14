package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Member;

/**
 * DB���� ȸ�� �ϳ� ����. PK�� ���� ȸ�� ID�� ��������� ȸ�� ��ü�� �Ķ���ͷ� ���.
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
	 *            ���� ������
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static void doAction(String originId, Member member)
			throws SQLException {
		
		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
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
		
		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
