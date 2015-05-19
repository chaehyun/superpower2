package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Elements.Coupon;

/**
 * 쿠폰을 자동으로 만들어줌. 맞춤형 쿠폰을 줄때 마땅한게 없을 때 호출됨.
 * 우선 새 쿠폰코드(String)와 해당 major에 맞는 상품코드(String),
 * 10퍼센트 할인, 한달짜리인 쿠폰 생성.
 * 
 * @author Seongjun
 * @since 2015/5/16
 * @version 2015/5/19
 */
public class CreateCouponAuto {

	/**
	 * @param major
	 * @return 자동으로 만든 쿠폰 객체
	 * @throws SQLException
	 *             SQL 에러 시 발생
	 */
	@SuppressWarnings("deprecation")
	public static Coupon doAction(String major) throws SQLException {

		// 새 쿠폰코드와 뒷번호
		String newCCode = "coupon";
		int num = 0;

		// 쿼리를 통해 쿠폰코드가 "coupon*"인 것들을 찾음
		String query = "select c_code from coupon where c_code like 'coupon%'";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		// 결과레코드를 보며 couponN으로 가능한 것 찾기
		List<String> existingCCodes = new ArrayList<String>();
		while (resultSet.next()) {
			existingCCodes.add(resultSet.getString("c_code"));
		}
		while (existingCCodes.contains(newCCode + num)) {
			num++;
		}

		// 새로운 쿠폰코드 탄생 !!!
		newCCode += num;

		// 파라미터로 받은 major에 해당하는 상품레코드들을 조회
		query = "select i_code from item where major=?";
		pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(query);
		pstmt.setString(1, major);
		resultSet = pstmt.executeQuery();

		// 리스트화
		List<String> existingICodes = new ArrayList<String>();
		while (resultSet.next()) {
			existingICodes.add(resultSet.getString("i_code"));
		}

		// 상품코드중 랜덤으로 배정. (인덱스 0 ~ n-1중 한개)
		String newICode = existingICodes.get(new Random()
				.nextInt(existingICodes.size() - 1));

		// 새 쿠폰을 만듬
		Coupon newCoupon = new Coupon();
		newCoupon.setc_code(newCCode);
		newCoupon.seti_code(newICode);
		newCoupon.setdiscount(10);
		newCoupon.setbegin_date(new Date());
		newCoupon.setend_date(new Date());
		newCoupon.getend_date().setDate(newCoupon.getend_date().getDate() + 30);

		// 쿠폰 만드는 쿼리 실행
		InsertCoupon.doAction(newCoupon);

		return newCoupon;
	}
}
