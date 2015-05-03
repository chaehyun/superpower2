package Communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Ŭ���̾�Ʈ���� ���񽺸� �����ϴ� ������ Ŭ����.
 * 
 * @author Minji, Seongjun
 * @since 2015/5/1
 * @version 2015/5/3
 */
public class ClientThread extends Thread {

	/**
	 * ��� ������
	 */
	private ServerThread serverThread;	// ���� ������ (parent)
	private Socket clientSocket;		// Ŭ���̾�Ʈ ����
	
	private ObjectInputStream ois;		// ��ü�Է½�Ʈ�� 
	private ObjectOutputStream oos;		// ��ü��½�Ʈ��
	
	/**
	 * ������. ���� ��������� �ʱ�ȭ.
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
	 * ������ ����κ�. while������ Ŭ���̾�Ʈ�κ���
	 * �޽����� �ް� ���񽺸� ������.
	 */
	@Override
	public void run() {
		
		try {
			
			// ����� ��Ʈ�� ����
			this.ois = new ObjectInputStream(this.clientSocket.getInputStream());
			this.oos = new ObjectOutputStream(this.clientSocket.getOutputStream());			

			// ���� ����
			while(true) {
			
				// Ŭ���̾�Ʈ�κ��� �޽����� ����
				Object recvMsg = null;
				if((recvMsg = ois.readObject()) == null) {
					throw new Exception("received null message");
				}
				
				// �޽��� ��� ���� ���� ���� �� ����
				//
				// (empty yet.)
				//
			}
			
		} catch(Exception e) {
			System.out.println("ClientThread.run()���� ���� �߻� : " + e.getMessage());
		}
		
		// Ŭ���̾�Ʈ ���� �� �������� ����Ʈ�κ��� ���ŵ�.
		this.closeClient();
		this.serverThread.removeClientFromList(this);
	}
	
	/**
	 * Ŭ���̾�Ʈ ����. �ܼ��� ������ run �κ��� ������.
	 */
	public void startClient() {
		
		this.start();
	}
	
	/**
	 * Ŭ���̾�Ʈ ����. ����½�Ʈ���� ������ ������.
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
			System.out.println("ClientThread.stopClient()���� ���� �߻� : " + e.getMessage());
		}
	}
}
