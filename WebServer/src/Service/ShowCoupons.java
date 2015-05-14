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
 * 특정 고객의 쿠폰 목록을 조회. 회원ID를 파라미터로 함
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

		// 해당 회원ID의 소유 쿠폰 수 카운트
		int count = 0;
		for (Ownership ownership : GetAllOwnership.doAction()) {
			if (id.equals(ownership.getId())) {
				count++;
			}
		}

		// JSON화
		JSONObject resultObject = new JSONObject();
		resultObject.put("MessageType", "res_coupon_list_count");
		resultObject.put("Count", count);
		
		return resultObject;
	}
	
	/**
	 * @param id
	 *            회원ID
	 * @return 쿠폰데이터
	 */
	synchronized public static JSONObject getCoupons(String id)
			throws JSONException, SQLException {

		// 헤더 추가
		JSONObject resultObject = new JSONObject();
		resultObject.put("MessageType", "res_coupon_list_datas");

		// 데이터 부분
		JSONArray jsonArray = new JSONArray();
		for (Ownership ownership : GetAllOwnership.doAction()) {
			
			// 소유테이블의 레코드 중 해당 회원의 ID와 비교
			if (id.equals(ownership.getId())) {
				
				// 소유 레코드의 쿠폰코드와 상품코드를 이용하여 객체 찾음
				Coupon coupon = GetCoupon.doAction(ownership.getC_code());
				Item item = GetItem.doAction(coupon.geti_code());
				
				// jsonArray에 넣을 쿠폰데이터
				JSONObject jsonCoupon = new JSONObject();
				jsonCoupon.put("Code", coupon.getc_code());
				jsonCoupon.put("ItemName", item.getminor());
				jsonCoupon.put("Price", item.getprice());
				jsonCoupon.put("Discount", coupon.getdiscount());
				jsonCoupon.put("StartDate", new SimpleDateFormat("yyyy-MM-dd")
						.format(coupon.getbegin_date()));
				jsonCoupon.put("EndDate", new SimpleDateFormat("yyyy-MM-dd")
						.format(coupon.getbegin_date()));

				// jsonArray에 넣음
				jsonArray.put(jsonCoupon);
			}
		}
		
		// 쿠폰 관련 데이터 넣음
		resultObject.put("Coupons",jsonArray);

		return resultObject;
	}
}
