package Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Coupon;

/**
 * DB에서 쿠폰 하나 수정. PK인 기존 쿠폰코드와 변경사항인 쿠폰 객체를 파라미터로 사용.
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
	 *            변경 데이터
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static void doAction(String originCode, Coupon coupon)
			throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);
		
		// 쿼리 실행
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

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
