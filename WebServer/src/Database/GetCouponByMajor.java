package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Coupon;

public class GetCouponByMajor {

	public static List<Coupon> doAction(String major) throws SQLException {

		// 쿼리 실행
		String query = "select coupon.* from coupon, item  where "
				+ "item.major=? and item.i_code=coupon.i_code";

		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(query);
		pstmt.setString(1, major);
		ResultSet rs = pstmt.executeQuery();

		// 결과들을 ArrayList에 저장
		List<Coupon> couponList = new ArrayList<Coupon>();

		while (rs.next()) {
			Coupon coupon = new Coupon();
			coupon.setc_code(rs.getString("c_code"));
			coupon.seti_code(rs.getString("i_code"));
			coupon.setdiscount(rs.getInt("discount"));
			coupon.setbegin_date(rs.getDate("begin_date"));
			coupon.setend_date(rs.getDate("end_date"));

			System.out.println(">" + coupon.getc_code());
			
			couponList.add(coupon);
		}
		
		System.out.println("success - GetCouponByMajor.doAction()");

		return couponList;
	}
}
