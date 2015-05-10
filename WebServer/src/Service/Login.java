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
		int count = 0;
		
		try{
			String query = "select password, count, logflag from member where id = ?";	
			PreparedStatement pstmt = DbConnector.getInstance().getConnection().prepareStatement(query);
			
			pstmt.setString(1,  userid);
			
			ResultSet rs = pstmt.executeQuery();
			
			// userid �� pwd�� count, flag�о��
			while(rs.next()){
				pwd = rs.getString("password");
				count = rs.getInt("count");
				flag = rs.getString("logflag");
			}
			
		}catch(Exception e){
			System.err.println("sql error = " + e.getMessage());
		}
		
		// password��ġ �� flag���� false�̸� true
		if(userpwd.equals(pwd) && flag.equals("f")){
			try {
				response.put("MessageType", "res_login");
				response.put("Result", true);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// logflag�� count set
			setlogflag(userid, true);
			setcount(userid, count++);
		}
		else{
			try {
				response.put("MessageType", "res_login");
				response.put("Result", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		try {
			System.out.println("�α��� �õ� : " + userid + " " + userpwd + " " + (response.getBoolean("Result") ? "true" : "false"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	

	// logflag ��ȭ (�ߺ� �α��� ����)
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
	
	// ����� count++
	public static void setcount(String userid, int count){
		
		try{
			DbConnector.getInstance().getConnection().setAutoCommit(false);
			
			String query = "update member set count = ? where id = ?";
			PreparedStatement pstmt = DbConnector.getInstance().getConnection().prepareStatement(query);
			pstmt.setInt(1,  count);
			pstmt.setString(2,  userid);
			int result = pstmt.executeUpdate();
			DbConnector.getInstance().getConnection().commit();
			DbConnector.getInstance().getConnection().setAutoCommit(true);
			
		}catch(Exception e){
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
}
