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
 * Ŭ���̾�Ʈ���� ���񽺸� �����ϴ� ������ Ŭ����.
 * 
 * @author Minji, Seongjun
 * @since 2015/5/1
 * @version 2015/5/7
 */
public class ClientThread extends Thread {

	/**
	 * ��� ������
	 */
	private ServerThread serverThread; // ���� ������ (parent)
	private Socket clientSocket; // Ŭ���̾�Ʈ ����

	private BufferedReader bufferedReader;
	private PrintWriter printWriter;

	private String id;

	/**
	 * ������. ���� ��������� �ʱ�ȭ.
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
	 * ������ ����κ�. while������ Ŭ���̾�Ʈ�κ��� �޽����� �ް� ���񽺸� ������.
	 */
	@Override
	public void run() {

		try {

			// ����� ��Ʈ�� ����
			this.bufferedReader = new BufferedReader(new InputStreamReader(
					new DataInputStream(this.clientSocket.getInputStream())));
			this.printWriter = new PrintWriter(new DataOutputStream(
					this.clientSocket.getOutputStream()));

			// ���� ����
			while (true) {

				// Ŭ���̾�Ʈ�κ��� JSON �޽����� ����
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

		// Ŭ���̾�Ʈ ���� �� �������� ����Ʈ�κ��� ���ŵ�.
		System.out.println("Client ���� : "
				+ clientSocket.getInetAddress().getHostName());

		this.closeClient();
		this.serverThread.removeClientFromList(this);
	}

	/**
	 * Ŭ���̾�Ʈ ����. ����½�Ʈ���� ������ ������.
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
			System.out.println("ClientThread.stopClient()���� ���� �߻� : "
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

		// ��� JSON�� Ŭ���̾�Ʈ�� ����
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
