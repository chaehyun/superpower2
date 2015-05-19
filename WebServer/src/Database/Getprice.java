package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Getprice {
public static int getprice(String i_code) throws SQLException{
		
		int price = 0;
		
		String sql = "select price from item where i_code = ?";
		
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, i_code);
		pstmt.executeQuery();
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()){
			price = rs.getInt("price");
		}
		
		return price;
	}
}
