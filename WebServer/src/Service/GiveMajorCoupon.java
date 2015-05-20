package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Database.CreateCouponAuto;
import Database.GetCouponByMajor;
import Database.GetLocationFromBeacon;
import Database.GetPersonalCoupon;
import Database.Getminorid;
import Database.Getprice;
import Elements.Coupon;
import Elements.Ownership;

/**
 * �� �������� ���ܿ� ���� �� �ش��ϴ� ������ ����.
 * 
 * @author Seongjun, Minji
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class GiveMajorCoupon {

	/**
	 * 
	 * @param userId
	 * @param macAddr
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONObject doAction(String userId, String macAddr)
			throws SQLException, JSONException {

		JSONObject response = null;

		// �ش� ���� MAC�� ���� ��ġ�� DB���� ������.
		String major = GetLocationFromBeacon.doAction(macAddr);

		// i_code�� ���� �˾Ƴ��� �� ��
		String minor;
		int price;

		// major�� �ش��ϴ� coupon
		List<Coupon> couponList = new ArrayList<Coupon>();
		couponList = GetCouponByMajor.doAction(major);

		// ���� �����ϰ� �ִ� coupon
		List<Ownership> mycouponList = new ArrayList<Ownership>();
		mycouponList = GetPersonalCoupon.doAction(userId);

		for (int i = 0; i < couponList.size(); i++) {
			for (int j = 0; j < mycouponList.size(); j++) {
				if (couponList.get(i).getc_code()
						.equals(mycouponList.get(j).getC_code()) == false) {

					// ���� �ʴٸ� coupon�� send

					// 1) i_code�� ������ ��ǰ���̺��� ��ǰ�̸�get
					minor = Getminorid.getminor(couponList.get(i).geti_code());
					// 2) i_code�� ������ ��ǰ���̺��� price get
					price = Getprice.getprice(couponList.get(i).geti_code());

					response.put("MessageType", "enter_coupon");
					response.put("Code", couponList.get(i).getc_code());
					response.put("ItemName", minor);
					response.put("Price", price);
					response.put("Discount", couponList.get(i).getdiscount());
					response.put("StartDate", couponList.get(i).getbegin_date());
					response.put("EndDate", couponList.get(i).getend_date());

					return response;

				}
			}
		}

		// ���ο� ���� ź��
		Coupon newcoupon = new Coupon();
		newcoupon = CreateCouponAuto.doAction(major);

		// 1) i_code�� ������ ��ǰ���̺��� ��ǰ�̸�get
		minor = Getminorid.getminor(newcoupon.geti_code());
		// 2) i_code�� ������ ��ǰ���̺��� price get
		price = Getprice.getprice(newcoupon.geti_code());

		response.put("MessageType", "major_coupon");
		response.put("Code", newcoupon.getc_code());
		response.put("ItemName", minor);
		response.put("Price", price);
		response.put("Discount", newcoupon.getdiscount());
		response.put("StartDate", newcoupon.getbegin_date());
		response.put("EndDate", newcoupon.getend_date());

		return response;
	}
}
