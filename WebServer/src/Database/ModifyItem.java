package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Item;

/**
 * DB에서 상품 하나 수정. PK인 기존 상품코드와 변경사항인 상품 객체를 파라미터로 사용.
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class ModifyItem {

	/**
	 * @param originCode
	 *            primary key
	 * @param item
	 *            변경 데이터
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	synchronized public static void doAction(String originCode, Item item)
			throws SQLException {

		// 오토커밋 비활성화
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// 쿼리 실행
		String sql = "update item set i_code=?, major=?, middle=?, minor=?, sales_volume=?, total_stock=?, price=?, image=? where i_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, item.geti_code());
		pstmt.setString(2, item.getmajor());
		pstmt.setString(3, item.getmiddle());
		pstmt.setString(4, item.getminor());
		pstmt.setInt(5, item.getsales_volume());
		pstmt.setInt(6, item.gettotal_stock());
		pstmt.setInt(7, item.getprice());
		pstmt.setString(8, item.getimage());
		pstmt.setString(9, originCode);
		pstmt.executeUpdate();

		// 커밋
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
