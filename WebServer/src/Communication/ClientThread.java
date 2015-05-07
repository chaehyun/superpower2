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
	}

	/**
	 * ������ ����κ�. while������ Ŭ���̾�Ʈ�κ��� �޽����� �ް� ���񽺸� ������.
	 */
	@Override
	public void run() {

		try {

			// ����� ��Ʈ�� ����
			this.bufferedReader = new BufferedReader(new InputStreamReader(
					this.clientSocket.getInputStream()));
			this.printWriter = new PrintWriter(new OutputStreamWriter(
					this.clientSocket.getOutputStream()));

			// ���� ����
			while (true) {

				// Ŭ���̾�Ʈ�κ��� JSON �޽����� ����
				StringBuffer buffer = new StringBuffer();
				String line = null;
				while((line = this.bufferedReader.readLine()) != null) {
					buffer.append(line);
				}
				JSONObject recvMsg = new JSONObject(buffer.toString());
				System.out.println("Ŭ���̾�Ʈ�κ��� �޽��� �޾���");

				// �޽��� Ÿ�Ժ��� ���� �� ����� sendMsg�� ���� �غ�
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

				// ��� JSON�� Ŭ���̾�Ʈ�� ����
				this.printWriter.println(sendMsg.toString());
				this.printWriter.flush();
				System.out.println("Ŭ���̾�Ʈ���� �޽��� ����");
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

		} catch (Exception e) {
			System.out.println("ClientThread.stopClient()���� ���� �߻� : "
					+ e.getMessage());
		}
	}
}
