package Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Purchase;

/**
 * DB���� ���� �ϳ� ����. PK�� ���� �����ڵ�� ��������� ���� ��ü�� �Ķ���ͷ� ���.
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class ModifyPurchase {

	/**
	 * @param originCode
	 *            primary key
	 * @param purchase
	 *            ���� ������
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static void doAction(String code, Purchase purchase)
			throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "update purchase set p_code=?, id=?, i_code=?, count=?, pur_date=? where p_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, purchase.getP_code());
		pstmt.setString(2, purchase.getId());
		pstmt.setString(3, purchase.getI_code());
		pstmt.setInt(4, purchase.getCount());
		pstmt.setDate(5, new Date(purchase.getPur_date().getTime()));
		pstmt.setString(6, code);
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
