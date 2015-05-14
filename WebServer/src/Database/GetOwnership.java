package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Elements.Ownership;

/**
 * DB로부터 소유 레코드를 가져옴. FK인 회원ID와 쿠폰코드를 파라미터로 사용
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetOwnership {

	/**
	 * @param id
	 *            foreign key
	 * @param cCode
	 *            foreign key
	 * @return 소유 객체 or null
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static Ownership doAction(String id, String cCode)
			throws SQLException {

		// DTO
		Ownership ownership = null;

		// 쿼리 실행
		String sql = "select * from ownership where id=? and c_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, cCode);
		ResultSet resultSet = pstmt.executeQuery();

		// 결과 레코드를 객체에 저장
		if (resultSet.next()) {
			ownership = new Ownership();
			ownership.setId(id);
			ownership.setC_code(cCode);
			ownership.setUsed(resultSet.getString("used"));
		}

		return ownership;
	}
}
