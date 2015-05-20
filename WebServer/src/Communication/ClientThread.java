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
 * 클라이언트에게 서비스를 제공하는 스레드 클래스.
 * 
 * @author Minji, Seongjun
 * @since 2015/5/1
 * @version 2015/5/14
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

	/**
	 * 로그인 체크한 결과를 보냄.
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

		// 결과 JSON을 클라이언트로 보냄
		this.printWriter.println(sendMsg.toString());
		this.printWriter.flush();
	}

	/**
	 * 베스트 상품 목록을 보냄
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
	 * 해당 회원의 쿠폰리스트를 보냄
	 * 
	 * @param recvMsg
	 * @throws JSONException
	 * @throws SQLException
	 */
	public void sendCouponList(JSONObject recvMsg) throws Exception {

		// 해당 회원의 쿠폰 수를 구함
		this.printWriter.println(ShowCoupons.getCouponCount(this.id));
		this.printWriter.flush();

		// ACK를 받음. 수신실패하거나 헤더가 다를경우 예외발생
		String ack = this.bufferedReader.readLine();
		if (ack == null
				|| !(new JSONObject(ack).getString("MessageType")
						.equals("req_coupon_list_ack"))) {
			throw new Exception("failed to receive ACK");
		}

		// 해당 회원이 소유하는 쿠폰 데이터 덩어리를 소켓으로 보냄
		this.printWriter.println(ShowCoupons.getCoupons(this.id));
		this.printWriter.flush();
	}

	/**
	 * 해당회원이 소유하는 쿠폰중 하나를 사용하고 그 결과를 반환함.
	 * 
	 * @param recvMsg
	 * @throws SQLException
	 * @throws JSONException
	 */
	public void sendUseCoupon(JSONObject recvMsg) throws SQLException,
			JSONException {

		// 사용 결과 JSON을 클라이언트로 보냄
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
		// 입장 쿠폰
		if ((GetLocationFromBeacon.doAction(mac_addr)).equals("entrance")) {
			response = GivePersonalCoupon.givepersonalcoupon(recvMsg
					.getString("ID"));
		}
		// 진열대쿠폰
		else {
			response = GiveMajorCoupon.doAction(recvMsg.getString("ID"),
					mac_addr);
		}

		// 결과 JSON을 클라이언트로 보냄
		this.printWriter.println(response);
		this.printWriter.flush();
	}
}
