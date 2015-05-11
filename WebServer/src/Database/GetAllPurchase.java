package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Purchase;

/**
 * 구매 테이블의 모든 레코드를 받음. 새로고침용
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetAllPurchase {

	/**
	 * @return 구매 리스트
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	synchronized public static List<Purchase> doAction() throws SQLException {

		// 쿼리 실행
		String sql = "select * from purchase";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// 결과들을 ArrayList에 저장
		List<Purchase> purchaseList = new ArrayList<Purchase>();

		while (resultSet.next()) {
			Purchase purchase = new Purchase();
			purchase.setP_code(resultSet.getString("p_code"));
			purchase.setId(resultSet.getString("id"));
			purchase.setI_code(resultSet.getString("i_code"));
			purchase.setCount(resultSet.getInt("count"));
			purchase.setPur_date(resultSet.getDate("pur_date"));

			purchaseList.add(purchase);
		}

		return purchaseList;
	}
}
