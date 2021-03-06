package Communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 클라이언트에게 서비스를 제공하는 스레드 클래스.
 * 
 * @author Minji, Seongjun
 * @since 2015/5/1
 * @version 2015/5/3
 */
public class ClientThread extends Thread {

	/**
	 * 멤버 변수들
	 */
	private ServerThread serverThread;	// 서버 스레드 (parent)
	private Socket clientSocket;		// 클라이언트 소켓
	
	private ObjectInputStream ois;		// 객체입력스트림 
	private ObjectOutputStream oos;		// 객체출력스트림
	
	/**
	 * 생성자. 각종 멤버변수를 초기화.
	 * 
	 * @param serverThread
	 * @param clientSocket
	 */
	public ClientThread(ServerThread serverThread, Socket clientSocket) {
		
		this.serverThread = serverThread;
		this.clientSocket = clientSocket;
		this.ois = null;
		this.oos = null;
	}

	/**
	 * 스레드 실행부분. while문에서 클라이언트로부터
	 * 메시지를 받고 서비스를 제공함.
	 */
	@Override
	public void run() {
		
		try {
			
			// 입출력 스트림 설정
			this.oos = new ObjectOutputStream(this.clientSocket.getOutputStream());	
			this.ois = new ObjectInputStream(this.clientSocket.getInputStream());		

			// 서비스 시작
			while(true) {
			
				// 클라이언트로부터 메시지를 받음 (Test)
				Packet recvPacket = null;
				if((recvPacket = (Packet) ois.readObject()) == null) {
					break;
				}
				
				// 메시지 헤더 별로 서비스 수행 후 답장 (Test)				
				System.out.println( clientSocket.getInetAddress().getHostName() + " : " + recvPacket.msg);				
				Packet sendPacket = new Packet("Hi"); 
				oos.writeObject(sendPacket);
				oos.flush();
			}
			
		} catch(IOException | ClassNotFoundException e) {
		}
		
		// 클라이언트 종료 및 서버에서 리스트로부터 제거됨.
		System.out.println("Client 퇴장 : " + clientSocket.getInetAddress().getHostName());
		this.closeClient();
		this.serverThread.removeClientFromList(this);
	}
	
	/**
	 * 클라이언트 종료. 입출력스트림과 소켓을 정리함.
	 */
	public void closeClient() {
		
		try {
			
			if(this.ois != null) {
				this.ois.close();
				this.ois = null;
			}
			
			if(this.oos != null) {
				this.oos.close();
				this.oos = null;
			}			
			
			if(this.clientSocket != null) {				
				this.clientSocket.close();
				this.clientSocket = null;
			}
			
		} catch(Exception e) {
			System.out.println("ClientThread.stopClient()에서 예외 발생 : " + e.getMessage());
		}
	}
}
