package Communication;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
<<<<<<< HEAD
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

=======
// 변경
>>>>>>> refs/heads/minji
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

	private Scanner inputScanner; // JSON 입력 스캐너
	private OutputStreamWriter outputWriter; // JSON 출력 스트림

	/**
	 * 생성자. 각종 멤버변수를 초기화.
	 * 
	 * @param serverThread
	 * @param clientSocket
	 */
	public ClientThread(ServerThread serverThread, Socket clientSocket) {

		this.serverThread = serverThread;
		this.clientSocket = clientSocket;
		this.inputScanner = null;
		this.outputWriter = null;
	}

	/**
	 * 스레드 실행부분. while문에서 클라이언트로부터 메시지를 받고 서비스를 제공함.
	 */
	@Override
	public void run() {

		try {

			// 입출력 스트림 설정
			this.inputScanner = new Scanner(this.clientSocket.getInputStream());
			this.outputWriter = new OutputStreamWriter(
					this.clientSocket.getOutputStream(), StandardCharsets.UTF_8);

			// 서비스 시작
			while (true) {

				// 클라이언트로부터 JSON 메시지를 받음
				JSONObject recvMsg = new JSONObject(this.inputScanner
						.useDelimiter("\\A").next());

				// 메시지 타입별로 서비스 후 결과를 sendMsg로 보낼 준비
				JSONObject sendMsg = null;
				switch (recvMsg.getString("MessageType")) {
				case "req_login":
					break;
				case "req_best_list":
					break;
				case "req_coupon_list":
					break;
				}

				// 결과 JSON을 클라이언트로 보냄				
				this.outputWriter.write(sendMsg.toString());
				
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

			if (this.inputScanner != null) {
				this.inputScanner.close();
				this.inputScanner = null;
			}

			if (this.outputWriter != null) {
				this.outputWriter.close();
				this.outputWriter = null;
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
