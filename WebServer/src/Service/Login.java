package Service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONObject;

import Database.DbConnector;

public class Login{
	
	public JSONObject logincheck(String userid, String userpwd){
		
		JSONObject response = new JSONObject();
		
		String pwd = null;
		String flag = null;
		
		try{
			String query = "select pwd, logflag from member where id = ?";	
			PreparedStatement pstmt = DbConnector.getInstance().getConnection().prepareStatement(query);
			
			pstmt.setString(1,  userid);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				pwd = rs.getString("password");
				flag = rs.getString("logflag");
			}
		}catch(Exception e){
			System.err.println("sql error = " + e.getMessage());
		}
		
		if(userpwd.equals(pwd) == true && flag.equals("f")){
			response.put("MessageType", "res_login");
			response.put("Result", true);
		}
		else{
			response.put("MessageType", "res_login");
			response.put("Result", false)
		}
	}
	
}
