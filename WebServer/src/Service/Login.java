package Service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONException;
import org.json.JSONObject;

import Database.DbConnector;

public class Login{
	
	public static JSONObject logincheck(String userid, String userpwd){
		
		JSONObject response = new JSONObject();
		
		String pwd = null;
		String flag = null;
		
		try{
			String query = "select password, logflag from member where id = ?";	
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
		
		// password일치 및 flag값이 false이면 true
		if(userpwd.equals(pwd) && flag.equals("f")){
			try {
				response.put("MessageType", "res_login");
				response.put("Result", true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			setlogflag(userid, true);
		}
		else{
			try {
				response.put("MessageType", "res_login");
				response.put("Result", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return response;
	}
	
	public static void setlogflag(String userid, boolean flag){
		try{
			DbConnector.getInstance().getConnection().setAutoCommit(false);
			
			String query = "update member set logflag = ? where id = ?";
			PreparedStatement pstmt = DbConnector.getInstance().getConnection().prepareStatement(query);
			pstmt.setString(1,  flag ? "t" : "f");
			pstmt.setString(2,  userid);
			int result = pstmt.executeUpdate();
			DbConnector.getInstance().getConnection().commit();
			DbConnector.getInstance().getConnection().setAutoCommit(true);
			
		}catch(Exception e){
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
}
