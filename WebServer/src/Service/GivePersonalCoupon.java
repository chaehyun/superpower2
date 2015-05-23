package Service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Database.CreateCouponAuto;
import Database.GetCoupon;
import Database.GetCouponByMajor;
import Database.GetPersonalCoupon;
import Database.Getmajorid;
import Database.Getminorid;
import Database.Getprice;
import Database.InsertOwnership;
import Elements.Coupon;
import Elements.Ownership;

public class GivePersonalCoupon {

	public static JSONObject givepersonalcoupon(String userid)
			throws SQLException, JSONException {

		JSONObject response = new JSONObject();

		// ���ɻ� major�޾ƿ�
		String major = UpdateFavorite.getFavorite(userid);
		
		// i_code�� ���� �˾Ƴ��� �� ��
		String minor;
		int price;

		// major�� �ش��ϴ� coupon
		List<Coupon> couponList = new ArrayList<Coupon>();
		couponList = GetCouponByMajor.doAction(major);

		// ���� �����ϰ� �ִ� coupon
		List<Ownership> mycouponList = new ArrayList<Ownership>();
		mycouponList = GetPersonalCoupon.doAction(userid);

		for (int i = 0; i < couponList.size(); i++) {
			for (int j = 0; j < mycouponList.size(); j++) {

				String mycouponmajor = Getmajorid.getmajor(GetCoupon.doAction(
						mycouponList.get(j).getC_code()).geti_code());

				if (contain(couponList, mycouponList.get(j).getC_code()) == false
						&& mycouponmajor.equals(major)) {

					// 1) i_code�� ������ ��ǰ���̺��� ��ǰ�̸�get
					minor = Getminorid.getminor(couponList.get(i).geti_code());
					// 2) i_code�� ������ ��ǰ���̺��� price get
					price = Getprice.getprice(couponList.get(i).geti_code());

					response.put("MessageType", "enter_coupon");
					response.put("Code", couponList.get(i).getc_code());
					response.put("ItemName", minor);
					response.put("Price", price);
					response.put("Discount", couponList.get(i).getdiscount());
					System.out.println(couponList.get(i).getdiscount());

					Date from = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
					String date_string = date.format(couponList.get(i)
							.getbegin_date());
					response.put("StartDate", date_string);

					date_string = date.format(couponList.get(i).getend_date());
					response.put("EndDate", date_string);

					Ownership newownership = new Ownership();
					newownership.setC_code(couponList.get(i).getc_code());
					newownership.setId(userid);
					newownership.setUsed("f");
					InsertOwnership.doAction(newownership);

					return response;

				}
			}
		}

		// ���ο� ���� ź��
		Coupon newcoupon = new Coupon();
		newcoupon = CreateCouponAuto.doAction(major);
		
		Ownership newownership = new Ownership();
		newownership.setC_code(newcoupon.getc_code());
		newownership.setId(userid);
		newownership.setUsed("f");
		InsertOwnership.doAction(newownership);

		// 1) i_code�� ������ ��ǰ���̺��� ��ǰ�̸�get
		minor = Getminorid.getminor(newcoupon.geti_code());
		// 2) i_code�� ������ ��ǰ���̺��� price get
		price = Getprice.getprice(newcoupon.geti_code());

		response.put("MessageType", "enter_coupon");
		response.put("Code", newcoupon.getc_code());
		response.put("ItemName", minor);
		response.put("Price", price);
		response.put("Discount", newcoupon.getdiscount());
		response.put("StartDate", newcoupon.getbegin_date());
		response.put("EndDate", newcoupon.getend_date());

		
		
		return response;

	}

	// ���� ����
	public static boolean contain(List<Coupon> couponlist, String c_code){
		
		for(int i=0; i<couponlist.size(); i++){
			if(couponlist.get(i).getc_code().equals(c_code)){
				return true;
			}
		}
		return false;
	}
}
