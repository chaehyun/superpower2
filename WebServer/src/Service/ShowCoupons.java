package Service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Database.GetAllOwnership;
import Database.GetCoupon;
import Database.GetItem;
import Elements.Coupon;
import Elements.Item;
import Elements.Ownership;

/**
 * Ư�� ���� ���� ����� ��ȸ. ȸ��ID�� �Ķ���ͷ� ��
 * 
 * @author Seongjun, Minji
 * @since 2015/5/12
 * @version 2015/5/14
 */
public class ShowCoupons {

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONObject getCouponCount(String id) throws SQLException, JSONException {

		// �ش� ȸ��ID�� ���� ���� �� ī��Ʈ
		int count = 0;
		for (Ownership ownership : GetAllOwnership.doAction()) {
			if (id.equals(ownership.getId())) {
				count++;
			}
		}

		// JSONȭ
		JSONObject resultObject = new JSONObject();
		resultObject.put("MessageType", "res_coupon_list_count");
		resultObject.put("Count", count);
		
		return resultObject;
	}
	
	/**
	 * @param id
	 *            ȸ��ID
	 * @return ����������
	 */
	synchronized public static JSONObject getCoupons(String id)
			throws JSONException, SQLException {

		// ��� �߰�
		JSONObject resultObject = new JSONObject();
		resultObject.put("MessageType", "res_coupon_list_datas");

		// ������ �κ�
		JSONArray jsonArray = new JSONArray();
		for (Ownership ownership : GetAllOwnership.doAction()) {
			
			// �������̺��� ���ڵ� �� �ش� ȸ���� ID�� ��
			if (id.equals(ownership.getId())) {
				
				// ���� ���ڵ��� �����ڵ�� ��ǰ�ڵ带 �̿��Ͽ� ��ü ã��
				Coupon coupon = GetCoupon.doAction(ownership.getC_code());
				Item item = GetItem.doAction(coupon.geti_code());
				
				// jsonArray�� ���� ����������
				JSONObject jsonCoupon = new JSONObject();
				jsonCoupon.put("Code", coupon.getc_code());
				jsonCoupon.put("ItemName", item.getminor());
				jsonCoupon.put("Price", item.getprice());
				jsonCoupon.put("Discount", coupon.getdiscount());
				jsonCoupon.put("StartDate", new SimpleDateFormat("yyyy-MM-dd")
						.format(coupon.getbegin_date()));
				jsonCoupon.put("EndDate", new SimpleDateFormat("yyyy-MM-dd")
						.format(coupon.getbegin_date()));

				// jsonArray�� ����
				jsonArray.put(jsonCoupon);
			}
		}
		
		// ���� ���� ������ ����
		resultObject.put("Coupons",jsonArray);

		return resultObject;
	}
}
