package Service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Database.CreateCouponAuto;
import Database.GetCouponByMajor;
import Database.GetLocationFromBeacon;
import Database.GetPersonalCoupon;
import Database.Getminorid;
import Database.Getprice;
import Database.InsertOwnership;
import Elements.Coupon;
import Elements.Ownership;

/**
 * �� �������� ���ܿ� ���� �� �ش��ϴ� ������ ����.
 * 
 * @author Seongjun, Minji
 * @since 2015/5/20
 * @version 2015/5/23
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

		JSONObject response = new JSONObject();

		// �ش� ���� MAC�� ���� ��ġ�� DB���� ������.
		String major = GetLocationFromBeacon.doAction(macAddr);

		// major�� �ش��ϴ� coupon
		List<Coupon> couponList = GetCouponByMajor.doAction(major);

		// ���� �����ϰ� �ִ� coupon
		List<Ownership> mycouponList = GetPersonalCoupon.doAction(userId);

		/*
		 * for (int i = 0; i < couponList.size(); i++) { for (int j = 0; j <
		 * mycouponList.size(); j++) {
		 * 
		 * String mycouponmajor = Getmajorid.getmajor(GetCoupon.doAction(
		 * mycouponList.get(j).getC_code()).geti_code());
		 * 
		 * if (contain(couponList, mycouponList.get(j).getC_code()) == false &&
		 * mycouponmajor.equals(major)) {
		 * 
		 * // ���� �ʴٸ� coupon�� send
		 * 
		 * // 1) i_code�� ������ ��ǰ���̺��� ��ǰ�̸�get String minor =
		 * Getminorid.getminor(couponList.get(i) .geti_code()); // 2) i_code��
		 * ������ ��ǰ���̺��� price get int price = Getprice
		 * .getprice(couponList.get(i).geti_code());
		 * 
		 * response.put("MessageType", "enter_coupon"); response.put("Code",
		 * couponList.get(i).getc_code()); response.put("ItemName", minor);
		 * response.put("Price", price); response.put("Discount",
		 * couponList.get(i).getdiscount());
		 * 
		 * SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd"); String
		 * date_string = date.format(couponList.get(i) .getbegin_date());
		 * response.put("StartDate", date_string);
		 * 
		 * date_string = date.format(couponList.get(i).getend_date());
		 * response.put("EndDate", date_string); ;
		 * 
		 * return response;
		 * 
		 * } } }
		 */

		// ���� ����Ʈ �� ���� �����ϰ� ���� ���� ���� �������� ��
		for (int i = 0; i < couponList.size(); i++) {
			if (!contain(mycouponList, couponList.get(i).getc_code())) {

				// 1) i_code�� ������ ��ǰ���̺��� ��ǰ�̸�get
				String minor = Getminorid.getminor(couponList.get(i)
						.geti_code());
				// 2) i_code�� ������ ��ǰ���̺��� price get
				int price = Getprice.getprice(couponList.get(i).geti_code());

				response.put("MessageType", "enter_coupon");
				response.put("Code", couponList.get(i).getc_code());
				response.put("ItemName", Getminorid.getminor(couponList.get(i)
						.geti_code()));
				response.put("Price", Getprice.getprice(couponList.get(i).geti_code()));
				response.put("Discount", couponList.get(i).getdiscount());
				response.put("StartDate", new SimpleDateFormat("yyyy-MM-dd")
						.format(couponList.get(i).getbegin_date()));
				response.put("EndDate", new SimpleDateFormat("yyyy-MM-dd")
						.format(couponList.get(i).getend_date()));

				Ownership newownership = new Ownership();
				newownership.setC_code(couponList.get(i).getc_code());
				newownership.setId(userId);
				newownership.setUsed("f");
				InsertOwnership.doAction(newownership);

				return response;
			}
		}

		// ���ο� ���� ź��
		Coupon newcoupon = CreateCouponAuto.doAction(major);

		response.put("MessageType", "major_coupon");
		response.put("Code", newcoupon.getc_code());
		response.put("ItemName", Getminorid.getminor(newcoupon.geti_code()));
		response.put("Price", Getprice.getprice(newcoupon.geti_code()));
		response.put("Discount", newcoupon.getdiscount());
		response.put("StartDate", new SimpleDateFormat("yyyy-MM-dd")
				.format(newcoupon.getbegin_date()));
		response.put("EndDate", new SimpleDateFormat("yyyy-MM-dd")
				.format(newcoupon.getend_date()));
		
		Ownership newownership = new Ownership();
		newownership.setC_code(newcoupon.getc_code());
		newownership.setId(userId);
		newownership.setUsed("f");
		InsertOwnership.doAction(newownership);
		
		return response;
	}

	// ���� ����
	public static boolean contain(List<Ownership> ownershipList, String c_code) {

		for (int i = 0; i < ownershipList.size(); i++) {
			if (ownershipList.get(i).getC_code().equals(c_code)) {
				return true;
			}
		}
		return false;
	}
}
