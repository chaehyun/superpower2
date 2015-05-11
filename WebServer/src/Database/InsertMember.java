package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Member;

/**
 * DB���� ȸ�� �ϳ� �߰�. ȸ�� ��ü�� �Ķ���ͷ� ���
 * 
 * @author Seongjun, Minji
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class InsertMember {

	/**
	 * @param member
	 *            ������ ��ü
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	synchronized public static void doAction(Member member) throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);
		
		// ���� ����
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

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
