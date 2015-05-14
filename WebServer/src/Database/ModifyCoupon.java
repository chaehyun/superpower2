package Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Coupon;

/**
 * DB���� ���� �ϳ� ����. PK�� ���� �����ڵ�� ��������� ���� ��ü�� �Ķ���ͷ� ���.
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class ModifyCoupon {

	/**
	 * @param originCode
	 *            primary key
	 * @param coupon
	 *            ���� ������
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static void doAction(String originCode, Coupon coupon)
			throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);
		
		// ���� ����
		String sql = "update coupon set c_code=?, i_code=?, discount=?, begin_date=?, end_date=? where c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, coupon.getc_code());
		pstmt.setString(2, coupon.geti_code());
		pstmt.setInt(3, coupon.getdiscount());
		pstmt.setDate(4, new Date(coupon.getbegin_date().getTime()));
		pstmt.setDate(5, new Date(coupon.getend_date().getTime()));
		pstmt.setString(6, originCode);
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
