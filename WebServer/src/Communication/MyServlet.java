package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;


public class MyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().println("works");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();

			JSONObject jsonResponse = new JSONObject();
			
			// Reading the request
			StringBuffer buffer = new StringBuffer();
			String line = null;
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				buffer.append(line);
			JSONObject requestMessage = new JSONObject(buffer.toString());
			System.out.println(buffer);

			// jsonResponse = getResponse(requestMessage);

			System.out.println(jsonResponse);
			out.println(jsonResponse.toString());
		} catch (IOException | JSONException e2) {
			e2.printStackTrace();
		}
	}

	private JSONObject getResponse(JSONObject requestMessage)
			throws JSONException, ParseException {
		String messageType = requestMessage.getString("MessageType");

		switch (messageType) {
		case "login":
			return MyServer.getInstance().login(requestMessage);
		default:
			JSONObject responseMessage = new JSONObject();
			responseMessage.put("Error", "Invalid message type");
			return responseMessage;
		}
	}
	
}
