package Communication;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyServer {

	private static MyServer instance = null;
	
	public JSONObject login(JSONObject requestMessage) throws JSONException {
		
		JSONObject response = new JSONObject();

		String userType = requestMessage.getString("usertype");
		String id = requestMessage.getString("ID");
		String pwd = requestMessage.getString("pwd");
		
		System.out.println("¾ÆÀÌµð: " + id);
		System.out.println("pwd: " + pwd);
		
		/*
		boolean loginValidate = (new logindemo().loginchk(id, pwd));
		if (loginValidate == true)
			response.put("valid", true);
		else
			response.put("valid", false);
		 */
		
		response.put("valid", true);
		return response;
	}
	
	public static MyServer getInstance() {
		if (instance == null) {
			instance = new MyServer();
		}
		return instance;
	}
}
