package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Coupon;

/**
 * 쿠폰 테이블의 모든 레코드를 받음. 새로고침용
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetAllCoupons {

	/**
	 * @return 쿠폰 리스트
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
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
