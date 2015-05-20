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

			// 1) 사용을 안했다면 DB에 사용함으로 갱신
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

			// 2) 구매테이블에 레코드 하나 추가하기전에 구매코드를 purchaseN 형태로 부여
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
			newPCode += num; // 새 구매코드 탄생!

			// 레코드에 추가할 DTO용 구매 객체 생성하고 DB에 추가
			Purchase purchase = new Purchase();
			purchase.setP_code(newPCode);
			purchase.setId(id);
			purchase.setI_code(GetCoupon.doAction(cCode).geti_code());
			purchase.setCount(1);
			purchase.setPur_date(new Date());
			InsertPurchase.doAction(purchase);

			// 3) 해당 상품 재고 하나 감소 & 판매수 증가. 기존 상품 레코드를
			// 객체화하여 수정 후 DB에 갱신
			Item item = GetItem.doAction(GetCoupon.doAction(cCode).geti_code());
			item.settotal_stock(item.gettotal_stock() - 1);
			item.setsales_volume(item.getsales_volume() + 1);
			ModifyItem.doAction(item.geti_code(), item);

			// 성공임
			resultObject.put("Result", true);
		}

		return resultObject;
	}
}
