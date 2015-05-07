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
// ����
>>>>>>> refs/heads/minji
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

	private Scanner inputScanner; // JSON �Է� ��ĳ��
	private OutputStreamWriter outputWriter; // JSON ��� ��Ʈ��

	/**
	 * ������. ���� ��������� �ʱ�ȭ.
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
	 * ������ ����κ�. while������ Ŭ���̾�Ʈ�κ��� �޽����� �ް� ���񽺸� ������.
	 */
	@Override
	public void run() {

		try {

			// ����� ��Ʈ�� ����
			this.inputScanner = new Scanner(this.clientSocket.getInputStream());
			this.outputWriter = new OutputStreamWriter(
					this.clientSocket.getOutputStream(), StandardCharsets.UTF_8);

			// ���� ����
			while (true) {

				// Ŭ���̾�Ʈ�κ��� JSON �޽����� ����
				JSONObject recvMsg = new JSONObject(this.inputScanner
						.useDelimiter("\\A").next());

				// �޽��� Ÿ�Ժ��� ���� �� ����� sendMsg�� ���� �غ�
				JSONObject sendMsg = null;
				switch (recvMsg.getString("MessageType")) {
				case "req_login":
					break;
				case "req_best_list":
					break;
				case "req_coupon_list":
					break;
				}

				// ��� JSON�� Ŭ���̾�Ʈ�� ����				
				this.outputWriter.write(sendMsg.toString());
				
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
			System.out.println("ClientThread.stopClient()���� ���� �߻� : "
					+ e.getMessage());
		}
	}
}
