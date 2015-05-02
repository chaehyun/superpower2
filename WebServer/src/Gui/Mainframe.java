package Gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;


public class Mainframe extends JFrame {

	private JPanel contentPane;
	private MemberPanel memberPanel;
	private CouponPanel couponPanel;
	private ProductPanel productPanel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	 * Create the frame.
	 */
	public Mainframe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 600);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 10, 760, 542);
		contentPane.add(tabbedPane);
		
		memberPanel = new MemberPanel();
		tabbedPane.addTab("회원", memberPanel);
		
		couponPanel = new CouponPanel();
		tabbedPane.addTab("쿠폰", couponPanel);

		productPanel = new ProductPanel();
		tabbedPane.addTab("상품", productPanel);
		
	}
}
