package Gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import Communication.ServerThread;
import Database.DbConnector;

/**
 * ���������� Ŭ����. �� ��ɺ� �гε��� ��� ū Ʋ.
 * 
 * @author Minji, Seongjun
 * @since 2015/5/1
 * @version 2015/5/4
 */
public class MainFrame extends JFrame {

	private JPanel contentPane; // ����Ʈ�� ����

	private MemberPanel memberPanel; 		// ȸ�� �г�
	private CouponPanel couponPanel;	 	// ���� �г�
	private ItemPanel itemPanel;			// ��ǰ �г�
	private OwnershipPanel ownershipPanel;	// ���� �г�
	private PurchasePanel purchasePanel;	// ���� �г�
	
	private ServerThread serverThread;	// ���� Ŭ����

	/**
	 * ���� �Լ�.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// ���������� GUI â ���
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * ������. �����Լ� ����� ȣ���.
	 */
	public MainFrame() {

		// ���� �������� Swing ��� ����
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 800, 600);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// â ����� �̺�Ʈ
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				closeForm();
			}
		});

		// �� ��ɺ� �г��� ���� �� �г� ����
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 10, 760, 542);
		contentPane.add(tabbedPane);

		// ȸ�� �г� ����
		memberPanel = new MemberPanel();
		tabbedPane.addTab("ȸ��", memberPanel);

		// ���� �г� ����
		couponPanel = new CouponPanel();
		tabbedPane.addTab("����", couponPanel);

		// ��ǰ �г� ����
		itemPanel = new ItemPanel();
		tabbedPane.addTab("��ǰ", itemPanel);
		
		// ���� �г� ����
		ownershipPanel = new OwnershipPanel();
		tabbedPane.addTab("����", ownershipPanel);
		
		// ���� �г� ����
		purchasePanel = new PurchasePanel();
		tabbedPane.addTab("����", purchasePanel);
		
		// ���� ����
		this.serverThread = new ServerThread();
		this.serverThread.startServer();
		
		// ��� ����
		DbConnector.getInstance();
	}

	/**
	 * ���α׷� ������ ���� ������ �������� �ϰ� ������.
	 */
	@SuppressWarnings("deprecation")
	public void closeForm()
	{
		// ���� Ȯ�� ���� â ���
		int res = JOptionPane.showConfirmDialog(null,
				"Are you sure?","Exit",
				JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
		
		// no �̸� return
		if(res != 0)
			return;
			
		// ���� ����
		if(this.serverThread.isRunning()) {
			this.serverThread.stop();
		}
		this.serverThread.stopServer();
		
		// ��� ����
		DbConnector.getInstance().closeConnection();
		
		// ���α׷� ����
		System.exit(0);
	}
}
