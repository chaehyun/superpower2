package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Coupon;

/**
 * DB로부터 쿠폰 레코드를 가져옴. PK인 쿠폰코드를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class GetCoupon {

	/**
	 * @param code
	 *            primary key
	 * @return 쿠폰 객체 or null
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static Coupon doAction(String code) throws SQLException {

		// DTO
		Coupon coupon = null;

		// 쿼리 실행
		String sql = "select * from coupon where c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		ResultSet resultSet = pstmt.executeQuery();

		// 결과 레코드를 객체에 저장
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
