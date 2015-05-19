package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Elements.Coupon;

/**
 * ������ �ڵ����� �������. ������ ������ �ٶ� �����Ѱ� ���� �� ȣ���.
 * �켱 �� �����ڵ�(String)�� �ش� major�� �´� ��ǰ�ڵ�(String),
 * 10�ۼ�Ʈ ����, �Ѵ�¥���� ���� ����.
 * 
 * @author Seongjun
 * @since 2015/5/16
 * @version 2015/5/19
 */
public class CreateCouponAuto {

	/**
	 * @param major
	 * @return �ڵ����� ���� ���� ��ü
	 * @throws SQLException
	 *             SQL ���� �� �߻�
	 */
	@SuppressWarnings("deprecation")
	public static Coupon doAction(String major) throws SQLException {

		// �� �����ڵ�� �޹�ȣ
		String newCCode = "coupon";
		int num = 0;

		// ������ ���� �����ڵ尡 "coupon*"�� �͵��� ã��
		String query = "select c_code from coupon where c_code like 'coupon%'";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(query);
		ResultSet resultSet = pstmt.executeQuery();

		// ������ڵ带 ���� couponN���� ������ �� ã��
		List<String> existingCCodes = new ArrayList<String>();
		while (resultSet.next()) {
			existingCCodes.add(resultSet.getString("c_code"));
		}
		while (existingCCodes.contains(newCCode + num)) {
			num++;
		}

		// ���ο� �����ڵ� ź�� !!!
		newCCode += num;

		// �Ķ���ͷ� ���� major�� �ش��ϴ� ��ǰ���ڵ���� ��ȸ
		query = "select i_code from item where major=?";
		pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(query);
		pstmt.setString(1, major);
		resultSet = pstmt.executeQuery();

		// ����Ʈȭ
		List<String> existingICodes = new ArrayList<String>();
		while (resultSet.next()) {
			existingICodes.add(resultSet.getString("i_code"));
		}

		// ��ǰ�ڵ��� �������� ����. (�ε��� 0 ~ n-1�� �Ѱ�)
		String newICode = existingICodes.get(new Random()
				.nextInt(existingICodes.size() - 1));

		// �� ������ ����
		Coupon newCoupon = new Coupon();
		newCoupon.setc_code(newCCode);
		newCoupon.seti_code(newICode);
		newCoupon.setdiscount(10);
		newCoupon.setbegin_date(new Date());
		newCoupon.setend_date(new Date());
		newCoupon.getend_date().setDate(newCoupon.getend_date().getDate() + 30);

		// ���� ����� ���� ����
		InsertCoupon.doAction(newCoupon);

		return newCoupon;
	}
}
