package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Coupon;
import Elements.Member;

public class GetAllCoupons {
	synchronized public static List<Coupon> doAction() throws SQLException {

		// 쿼리 실행
		String sql = "select * from coupon";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// 결과들을 ArrayList에 저장
		List<Coupon> couponList = new ArrayList<Coupon>();
		
		while (resultSet.next()) {
			Coupon coupon = new Coupon();
			coupon.setc_code(resultSet.getString("c_code"));
			coupon.seti_code(resultSet.getString("i_code"));
			coupon.setdiscount(resultSet.getInt("discount"));
			coupon.setbegin_date(resultSet.getDate("begin_date"));
			coupon.setend_date(resultSet.getDate("end_date"));

			couponList.add(coupon);
		}

		return couponList;
	}
}
