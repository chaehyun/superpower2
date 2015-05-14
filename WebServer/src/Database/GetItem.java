package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Item;

/**
 * DB로부터 상품 레코드를 가져옴. PK인 상품코드를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetItem {

	/**
	 * @param code
	 *            primary key
	 * @return 상품 객체 or null
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static Item doAction(String code) throws SQLException {

		// DTO
		Item item = null;

		// 쿼리 실행
		String sql = "select * from item where i_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, code);
		ResultSet resultSet = pstmt.executeQuery();

		// 결과 레코드를 객체에 저장
		if (resultSet.next()) {
			item = new Item();
			item.seti_code(code);
			item.setmajor(resultSet.getString("major"));
			item.setmiddle(resultSet.getString("middle"));
			item.setminor(resultSet.getString("minor"));
			item.setsales_volume(resultSet.getInt("sales_volume"));
			item.settotal_stock(resultSet.getInt("total_stock"));
			item.setprice(resultSet.getInt("price"));
			item.setimage(resultSet.getString("image"));
		}

		return item;
	}
}
