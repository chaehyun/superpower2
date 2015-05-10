package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Ownership;

public class InsertOwnership {
	public static void insertownership(Ownership newownership){
		String sql = null;
		sql = "insert into ownership value(?, ?, ?)";
		
		try {
			DbConnector.getInstance().getConnection().setAutoCommit(false);
			PreparedStatement pstmt = DbConnector.getInstance().getConnection()
					.prepareStatement(sql);
			pstmt.setString(1, newownership.getId());
			pstmt.setString(2, newownership.getC_code());
			pstmt.setString(3, newownership.getUsed());
			
			int result = pstmt.executeUpdate();
			
			DbConnector.getInstance().getConnection().commit();
			DbConnector.getInstance().getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
