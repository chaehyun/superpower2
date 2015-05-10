package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ���� �ϳ� ����. PK�� �����ڵ带 �Ķ���ͷ� ���
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class DeleteCoupon {

	/**
	 * @param code
	 *            primary key
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	synchronized public static void doAction(String code) throws SQLException {

		// ���� ����
		String sql = "delete from coupon where c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		pstmt.executeUpdate();
		
		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
	}
}
