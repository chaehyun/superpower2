package Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Coupon;

public class InsertCoupon {
	public static void doAction(Coupon newcoupon) throws SQLException {
		String sql = null;
		sql = "insert into coupon value(?, ?, ?, ?, ?)";

		DbConnector.getInstance().getConnection().setAutoCommit(false);
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, newcoupon.getc_code());
		pstmt.setString(2, newcoupon.geti_code());
		pstmt.setInt(3, newcoupon.getdiscount());
		java.sql.Date begin_date = new Date(newcoupon.getbegin_date().getTime());
		pstmt.setDate(4, begin_date);
		java.sql.Date end_date = new Date(newcoupon.getend_date().getTime());
		pstmt.setDate(5, end_date);

		pstmt.executeUpdate();

		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
