package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

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
	}

	/**
	 * 스레드 실행부분. while문에서 클라이언트로부터 메시지를 받고 서비스를 제공함.
	 */
	@Override
	public void run() {

		try {

			// 입출력 스트림 설정
			this.bufferedReader = new BufferedReader(new InputStreamReader(
					this.clientSocket.getInputStream()));
			this.printWriter = new PrintWriter(new OutputStreamWriter(
					this.clientSocket.getOutputStream()));

			// 서비스 시작
			while (true) {

				// 클라이언트로부터 JSON 메시지를 받음
				StringBuffer buffer = new StringBuffer();
				String line = null;
				while((line = this.bufferedReader.readLine()) != null) {
					buffer.append(line);
				}
				JSONObject recvMsg = new JSONObject(buffer.toString());
				System.out.println("클라이언트로부터 메시지 받았음");

				// 메시지 타입별로 서비스 후 결과를 sendMsg로 보낼 준비
				JSONObject sendMsg = null;
				switch (recvMsg.getString("MessageType")) {
				case "req_login":
					sendMsg = Login.logincheck(recvMsg.getString("ID"),
							recvMsg.getString("Password"));
					break;
				case "req_best_list":
					break;
				case "req_coupon_list":
					break;
				}

				// 결과 JSON을 클라이언트로 보냄
				this.printWriter.println(sendMsg.toString());
				this.printWriter.flush();
				System.out.println("클라이언트에게 메시지 보냄");
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

		} catch (Exception e) {
			System.out.println("ClientThread.stopClient()에서 예외 발생 : "
					+ e.getMessage());
		}
	}
}
