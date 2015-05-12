package Communication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Service.BestItem;
import Service.Login;

/**
 * 클라이언트에게 서비스를 제공하는 스레드 클래스.
 * 
 * @author Minji, Seongjun
 * @since 2015/5/1
 * @version 2015/5/7
 */
public class ClientThread extends Thread {

	/**
	 * 멤버 변수들
	 */
	private ServerThread serverThread; // 서버 스레드 (parent)
	private Socket clientSocket; // 클라이언트 소켓

	private BufferedReader bufferedReader;
	private PrintWriter printWriter;

	private String id;

	/**
	 * 생성자. 각종 멤버변수를 초기화.
	 * 
	 * @param serverThread
	 * @param clientSocket
	 */
	public ClientThread(ServerThread serverThread, Socket clientSocket) {

		this.serverThread = serverThread;
		this.clientSocket = clientSocket;
		this.bufferedReader = null;
		this.printWriter = null;
		this.id = "";
	}

	/**
	 * 스레드 실행부분. while문에서 클라이언트로부터 메시지를 받고 서비스를 제공함.
	 */
	@Override
	public void run() {

		try {

			// 입출력 스트림 설정
			this.bufferedReader = new BufferedReader(new InputStreamReader(
					new DataInputStream(this.clientSocket.getInputStream())));
			this.printWriter = new PrintWriter(new DataOutputStream(
					this.clientSocket.getOutputStream()));

			// 서비스 시작
			while (true) {

				// 클라이언트로부터 JSON 메시지를 받음
				String line = this.bufferedReader.readLine();
				if (line == null) {
					break;
				}
				JSONObject recvMsg = new JSONObject(line);
				
				switch (recvMsg.getString("MessageType")) {
				case "req_login":
					sendlogin(recvMsg);
					break;
				case "req_best_list":
					sendbestitem();
					break;
				case "req_coupon_list":
					break;
				}
			}
		} catch (IOException | JSONException e) {
		}

		// 클라이언트 종료 및 서버에서 리스트로부터 제거됨.
		System.out.println("Client 퇴장 : "
				+ clientSocket.getInetAddress().getHostName());

		this.closeClient();
		this.serverThread.removeClientFromList(this);
	}

	/**
	 * 클라이언트 종료. 입출력스트림과 소켓을 정리함.
	 */
	public void closeClient() {

		try {

			if (this.bufferedReader != null) {
				this.bufferedReader.close();
				this.bufferedReader = null;
			}

			if (this.printWriter != null) {
				this.printWriter.close();
				this.printWriter = null;
			}

			if (this.clientSocket != null) {
				this.clientSocket.close();
				this.clientSocket = null;
			}

			if (!"".equals(this.id)) {
				Login.setlogflag(this.id, false);
			}

		} catch (Exception e) {
			System.out.println("ClientThread.stopClient()에서 예외 발생 : "
					+ e.getMessage());
		}
	}
	
	public void sendlogin(JSONObject recvMsg) throws JSONException{
		
		JSONObject sendMsg = new JSONObject();
		
		sendMsg = Login.logincheck(recvMsg.getString("ID"),
				recvMsg.getString("Password"));
		if (sendMsg.getBoolean("Result")) {
			this.id = recvMsg.getString("ID");
		}

		// 결과 JSON을 클라이언트로 보냄
		this.printWriter.println(sendMsg.toString());
		this.printWriter.flush();
	}
	
	public void sendbestitem() throws JSONException{
		
		List<JSONObject> bestlist = new ArrayList<JSONObject>();
		bestlist = BestItem.getBestItems();
		
		for(int i=0; i<10; i++){
			JSONObject sendMsg = new JSONObject();
			sendMsg = bestlist.get(i);
			
			this.printWriter.println(sendMsg.toString());
			this.printWriter.flush();
		}
		
	}
}
