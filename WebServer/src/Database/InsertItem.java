package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Item;

public class InsertItem {
	public static void doAction(Item newitem) throws SQLException {
		String sql = null;
		sql = "insert into item value(?, ?, ?, ?, ?, ?, ?, ?)";

		DbConnector.getInstance().getConnection().setAutoCommit(false);
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, newitem.geti_code());
		pstmt.setString(2, newitem.getmajor());
		pstmt.setString(3, newitem.getmiddle());
		pstmt.setString(4, newitem.getminor());
		pstmt.setInt(5, newitem.getsales_volume());
		pstmt.setInt(6, newitem.gettotal_stock());
		pstmt.setInt(7, newitem.getprice());
		pstmt.setString(8, newitem.getimage());

		pstmt.executeUpdate();

		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
