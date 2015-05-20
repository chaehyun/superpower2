package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Database.DbConnector;
import Database.GetCoupon;
import Database.GetItem;
import Database.InsertPurchase;
import Database.ModifyItem;
import Elements.Item;
import Elements.Purchase;

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

			// 1) ����� ���ߴٸ� DB�� ��������� ����
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

			// 2) �������̺� ���ڵ� �ϳ� �߰��ϱ����� �����ڵ带 purchaseN ���·� �ο�
			String newPCode = "purchase";
			int num = 0;
			query = "select p_code from purchase where p_code='purchase%'";
			pstmt = DbConnector.getInstance().getConnection()
					.prepareStatement(query);
			resultSet = pstmt.executeQuery();
			List<String> existingPCodes = new ArrayList<String>();
			while (resultSet.next()) {
				existingPCodes.add(resultSet.getString("p_code"));
			}
			while (existingPCodes.contains(newPCode + num)) {
				num++;
			}
			newPCode += num; // �� �����ڵ� ź��!

			// ���ڵ忡 �߰��� DTO�� ���� ��ü �����ϰ� DB�� �߰�
			Purchase purchase = new Purchase();
			purchase.setP_code(newPCode);
			purchase.setId(id);
			purchase.setI_code(GetCoupon.doAction(cCode).geti_code());
			purchase.setCount(1);
			purchase.setPur_date(new Date());
			InsertPurchase.doAction(purchase);

			// 3) �ش� ��ǰ ��� �ϳ� ���� & �Ǹż� ����. ���� ��ǰ ���ڵ带
			// ��üȭ�Ͽ� ���� �� DB�� ����
			Item item = GetItem.doAction(GetCoupon.doAction(cCode).geti_code());
			item.settotal_stock(item.gettotal_stock() - 1);
			item.setsales_volume(item.getsales_volume() + 1);
			ModifyItem.doAction(item.geti_code(), item);

			// ������
			resultObject.put("Result", true);
		}

		return resultObject;
	}
}
