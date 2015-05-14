package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import Database.DbConnector;

/**
 * 특정 고객의 특정 쿠폰을 사용. 회원ID와 쿠폰코드를 파라미터로 함
 * 
 * @author Minji, Seongjun
 * @since 2015/5/14
 * @version 2015/5/14
 */
public class UseCoupon {

	/**
	 * @param id
	 *            회원ID
	 * @param cCode
	 *            쿠폰코드
	 * @return JSON 객체
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONObject use(String id, String cCode) throws SQLException,
			JSONException {

		// 결과용 객체. 헤더 붙임
		JSONObject resultObject = new JSONObject();
		resultObject.put("MessageType", "res_coupon_use");

		// 해당 회원의 해당쿠폰이 사용이 안한건지 확인
		String query = "select * from ownership where id=? and c_code=? and used=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(query);
		pstmt.setString(1, id);
		pstmt.setString(2, cCode);
		pstmt.setString(3, "f");

		// 조회 쿼리 실행
		ResultSet resultSet = pstmt.executeQuery();
		if (!resultSet.next()) {

			// 사용을 했거나 없다면 실패임
			resultObject.put("Result", false);

		} else {

			// 사용을 안했다면 DB에 사용함으로 갱신
			DbConnector.getInstance().getConnection().setAutoCommit(false);
			query = "update ownership set used=? where id=? and c_code=?";
			pstmt = DbConnector.getInstance().getConnection()
					.prepareStatement(query);
			pstmt.setString(1, "t");
			pstmt.setString(2, id);
			pstmt.setString(3, cCode);
			pstmt.executeUpdate();
			DbConnector.getInstance().getConnection().commit();
			DbConnector.getInstance().getConnection().setAutoCommit(false);

			// 성공임
			resultObject.put("Result", true);
		}

		return resultObject;
	}
}
