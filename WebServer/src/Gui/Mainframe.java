package Gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import Communication.ServerThread;

/**
 * ���������� Ŭ����. �� ��ɺ� �гε��� ��� ū Ʋ.
 * 
 * @author Minji, Seongjun
 * @since 2015/5/1
 * @version 2015/5/1
 */
public class Mainframe extends JFrame {

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
					Mainframe frame = new Mainframe();
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
	public Mainframe() {

		// ���� �������� Swing ��� ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
				
				// ���� Ȯ�� ���� â ���
				int res = JOptionPane.showConfirmDialog(null,
						"Are you sure?","Exit",
						JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
				
				// yes �̸� ���α׷� ����
				if(res == 0)
					CloseForm();
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
	}

	/**
	 * ���α׷� ������ ���� ������ �������� �ϰ� ������.
	 */
	@SuppressWarnings("deprecation")
	public void CloseForm()
	{
		if(this.serverThread.isRunning()) {
			serverThread.stop();
		}
		
		serverThread.stopServer();
		System.exit(0);
	}
}
