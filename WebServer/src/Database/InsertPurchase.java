package Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Purchase;

/**
 * DB���� ���� �ϳ� �߰�. ���� ��ü�� �Ķ���ͷ� ���
 * 
 * @author Seongjun, Minji
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class InsertPurchase {

	/**
	 * @param purchase
	 *            ������ ��ü
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static void doAction(Purchase purchase)
			throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "insert into purchase value(?, ?, ?, ?, ?)";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, purchase.getP_code());
		pstmt.setString(2, purchase.getId());
		pstmt.setString(3, purchase.getI_code());
		pstmt.setInt(4, purchase.getCount());
		pstmt.setDate(5, new Date(purchase.getPur_date().getTime()));
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
