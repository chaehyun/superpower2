package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ��� ������ �α��ο��θ� �ʱ�ȭ��
 * 
 * @author Seongjun, Minji
 *
 */
public class ResetLogFlag {

	/**
	 * �α��� ���� ��� �ʱ�ȭ. ���α׷� ���۽� ȣ���.
	 */
	public static void doAction() throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "update member set logflag=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, "f");
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
