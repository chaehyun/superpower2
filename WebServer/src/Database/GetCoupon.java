package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Coupon;

/**
 * DB�κ��� ���� ���ڵ带 ������. PK�� �����ڵ带 �Ķ���ͷ� ���
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class GetCoupon {

	/**
	 * @param code
	 *            primary key
	 * @return ���� ��ü or null
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static Coupon doAction(String code) throws SQLException {

		// DTO
		Coupon coupon = null;

		// ���� ����
		String sql = "select * from coupon where c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		ResultSet resultSet = pstmt.executeQuery();

		// ��� ���ڵ带 ��ü�� ����
		if (resultSet.next()) {
			coupon = new Coupon();
			coupon.setc_code(code);
			coupon.seti_code(resultSet.getString("i_code"));
			coupon.setdiscount(resultSet.getInt("discount"));
			coupon.setbegin_date(resultSet.getDate("begin_date"));
			coupon.setend_date(resultSet.getDate("end_date"));
		}

		return coupon;
	}
}
