package Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Purchase;

/**
 * DB에서 구매 하나 수정. PK인 기존 구매코드와 변경사항인 구매 객체를 파라미터로 사용.
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
	 *            변경 데이터
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static void doAction(String code, Purchase purchase)
			throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
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

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
