package Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login{
	
	public boolean logincheck(String userid, String userpwd){
		
		String pwd = null;
		
		try{
			String query = "select pwd from member where id = ?";	
			PreparedStatement pstmt = DbConnector.getInstance().getConnection().prepareStatement(query);
			
			pstmt.setString(1,  userid);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				pwd = rs.getString("pwd");
			}
		}catch(Exception e){
			System.err.println("sql error = " + e.getMessage());
		}
		
		if(userpwd.equals(pwd) == true)
			return true;
		else return false;
	}
	
}
