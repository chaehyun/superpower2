package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Purchase;

/**
 * DB로부터 구매 레코드를 가져옴. PK인 구매코드를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetPurchase {

	/**
	 * @param code
	 *            primary key
	 * @return 소유 객체 or null
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	synchronized public static Purchase doAction(String code)
			throws SQLException {

		// DTO
		Purchase purchase = null;

		// 쿼리 실행
		String sql = "select * from purchase where p_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		ResultSet resultSet = pstmt.executeQuery();

		// 결과 레코드를 객체에 저장
		if (resultSet.next()) {
			purchase = new Purchase();
			purchase.setP_code(code);
			purchase.setId(resultSet.getString("id"));
			purchase.setI_code(resultSet.getString("i_code"));
			purchase.setCount(resultSet.getInt("count"));
			purchase.setPur_date(resultSet.getDate("pur_date"));
		}

		return purchase;
	}
}
