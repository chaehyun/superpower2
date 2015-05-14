package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import Database.DbConnector;

/**
 * Ư�� ���� Ư�� ������ ���. ȸ��ID�� �����ڵ带 �Ķ���ͷ� ��
 * 
 * @author Minji, Seongjun
 * @since 2015/5/14
 * @version 2015/5/14
 */
public class UseCoupon {

	/**
	 * @param id
	 *            ȸ��ID
	 * @param cCode
	 *            �����ڵ�
	 * @return JSON ��ü
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONObject use(String id, String cCode) throws SQLException,
			JSONException {

		// ����� ��ü. ��� ����
		JSONObject resultObject = new JSONObject();
		resultObject.put("MessageType", "res_coupon_use");

		// �ش� ȸ���� �ش������� ����� ���Ѱ��� Ȯ��
		String query = "select * from ownership where id=? and c_code=? and used=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(query);
		pstmt.setString(1, id);
		pstmt.setString(2, cCode);
		pstmt.setString(3, "f");

		// ��ȸ ���� ����
		ResultSet resultSet = pstmt.executeQuery();
		if (!resultSet.next()) {

			// ����� �߰ų� ���ٸ� ������
			resultObject.put("Result", false);

		} else {

			// ����� ���ߴٸ� DB�� ��������� ����
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

			// ������
			resultObject.put("Result", true);
		}

		return resultObject;
	}
}
