package Communication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Database.GetLocationFromBeacon;
import Database.RegisterMember;
import Service.BestItem;
import Service.GiveMajorCoupon;
import Service.GivePersonalCoupon;
import Service.Login;
import Service.ShowCoupons;
import Service.UseCoupon;

/**
 * Ŭ���̾�Ʈ���� ���񽺸� �����ϴ� ������ Ŭ����.
 * 
 * @author Minji, Seongjun
 * @since 2015/5/1
 * @version 2015/5/14
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
				System.out.println(recvMsg.getString("MessageType"));
				switch (recvMsg.getString("MessageType")) {
				case "req_login":
					sendlogin(recvMsg);
					break;
				case "req_best_list":
					sendbestitem();
					break;
				case "req_coupon_list":
					sendCouponList(recvMsg);
					break;
				case "connect_beacon":
					sendGiveCoupon(recvMsg);
					break;
				case "req_coupon_use":
					sendUseCoupon(recvMsg);
					break;
				case "register":
					RegisterMember.doAction(recvMsg);
					break;
				}
			}
		} catch (Exception e) {
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

	/**
	 * �α��� üũ�� ����� ����.
	 * 
	 * @param recvMsg
	 * @throws JSONException
	 * @throws SQLException
	 */
	public void sendlogin(JSONObject recvMsg) throws JSONException,
			SQLException {

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

	/**
	 * ����Ʈ ��ǰ ����� ����
	 * 
	 * @throws JSONException
	 */
	public void sendbestitem() throws JSONException {

		List<JSONObject> bestlist = new ArrayList<JSONObject>();
		bestlist = BestItem.getBestItems();

		for (int i = 0; i < 10; i++) {
			JSONObject sendMsg = new JSONObject();
			sendMsg = bestlist.get(i);

			this.printWriter.println(sendMsg.toString());
			this.printWriter.flush();
		}
	}

	/**
	 * �ش� ȸ���� ��������Ʈ�� ����
	 * 
	 * @param recvMsg
	 * @throws JSONException
	 * @throws SQLException
	 */
	public void sendCouponList(JSONObject recvMsg) throws Exception {

		// �ش� ȸ���� ���� ���� ����
		this.printWriter.println(ShowCoupons.getCouponCount(this.id));
		this.printWriter.flush();

		// ACK�� ����. ���Ž����ϰų� ����� �ٸ���� ���ܹ߻�
		String ack = this.bufferedReader.readLine();
		if (ack == null
				|| !(new JSONObject(ack).getString("MessageType")
						.equals("req_coupon_list_ack"))) {
			throw new Exception("failed to receive ACK");
		}

		// �ش� ȸ���� �����ϴ� ���� ������ ����� �������� ����
		this.printWriter.println(ShowCoupons.getCoupons(this.id));
		this.printWriter.flush();
	}

	/**
	 * �ش�ȸ���� �����ϴ� ������ �ϳ��� ����ϰ� �� ����� ��ȯ��.
	 * 
	 * @param recvMsg
	 * @throws SQLException
	 * @throws JSONException
	 */
	public void sendUseCoupon(JSONObject recvMsg) throws SQLException,
			JSONException {

		// ��� ��� JSON�� Ŭ���̾�Ʈ�� ����
		this.printWriter.println(UseCoupon.use(this.id,
				recvMsg.getString("Code")));
		this.printWriter.flush();
	}

	/**
	 * @param recvMsg
	 * @return
	 * @throws JSONException
	 * @throws SQLException
	 */
	public void sendGiveCoupon(JSONObject recvMsg) throws JSONException,
			SQLException {

		JSONObject response = null;

		String mac_addr = recvMsg.getString("mac_addr");
		
		System.out.println("receive mac_addr");
		// ���� ����
		if ((GetLocationFromBeacon.doAction(mac_addr)).equals("entrance")) {
			response = GivePersonalCoupon.givepersonalcoupon(recvMsg
					.getString("ID"));
		}
		// ����������
		else {
			response = GiveMajorCoupon.doAction(recvMsg.getString("ID"),
					mac_addr);
		}

		// ��� JSON�� Ŭ���̾�Ʈ�� ����
		this.printWriter.println(response);
		this.printWriter.flush();
	}
}
