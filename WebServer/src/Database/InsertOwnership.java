package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Ownership;

public class InsertOwnership {
	public static void doAction(Ownership newownership)
			throws SQLException {
		String sql = null;
		sql = "insert into ownership value(?, ?, ?)";

		DbConnector.getInstance().getConnection().setAutoCommit(false);
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, newownership.getId());
		pstmt.setString(2, newownership.getC_code());
		pstmt.setString(3, newownership.getUsed());

		pstmt.executeUpdate();

		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
