package Gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Database.GetAllCoupons;
import Database.GetAllMembers;
import Database.InsertCoupon;
import Database.InsertOwnership;
import Elements.Coupon;
import Elements.Member;
import Elements.Ownership;

/**
 * 소유 추가, 수정을 위한 다이얼로그 클래스
 * 
 * @author Minji, Seongjun
 * @since 2015/5/7
 * @version 2015/5/7
 */
public class OwnershipDialog extends JDialog {

	private final JPanel contentPanel = new JPanel(); // Default

	private JComboBox<String> comboBoxId; 		// 아이디 콤보박스
	private JComboBox<String> comboBoxCCode; 	// 쿠폰코드 콤보박스
	private JComboBox<String> comboBoxUsed;		

	private boolean ok = false; // 확인,취소 버튼 여부

	/**
	 * 생성자. 소유 추가 버튼 누를 시 호출
	 */
	public OwnershipDialog() {
		this(null);
	}

	/**
	 * 생성자. 소유 수정 버튼 누를 시 호출
	 */
	public OwnershipDialog(Ownership info) {

		// 다이얼로그 속성 설정
		setTitle("\uC18C\uC720 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 262, 148);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane 설정
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// 아이디 레이블
		JLabel labelId = new JLabel("ID :");
		labelId.setHorizontalAlignment(SwingConstants.RIGHT);
		labelId.setBounds(12, 10, 95, 15);
		contentPanel.add(labelId);

		// 아이디 콤보박스
		comboBoxId = new JComboBox<String>();
		try {
			for (Member member : GetAllMembers.doAction()) {
				comboBoxId.addItem(member.getId());
			}
		} catch (SQLException e) {
			System.out.println("OwnershipDialog()에서 예외 발생 : " + e.getMessage());
		}
		comboBoxId.setSelectedIndex(0);
		comboBoxId.setBounds(119, 7, 116, 21);
		contentPanel.add(comboBoxId);

		// 쿠폰코드 레이블
		JLabel labelCCode = new JLabel("\uCFE0\uD3F0\uCF54\uB4DC :");
		labelCCode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCCode.setBounds(12, 35, 95, 15);
		contentPanel.add(labelCCode);

		// 쿠폰코드 콤보박스
		comboBoxCCode = new JComboBox<String>();
		try {
			for (Coupon coupon : GetAllCoupons.doAction()) {
				comboBoxCCode.addItem(coupon.getc_code());
			}
		} catch (SQLException e) {
			System.out.println("OwnershipDialog()에서 예외 발생 : " + e.getMessage());
		}
		comboBoxCCode.setSelectedIndex(0);
		comboBoxCCode.setBounds(119, 32, 116, 21);
		contentPanel.add(comboBoxCCode);

		// 사용여부 레이블
		JLabel labelUsed = new JLabel("\uC0AC\uC6A9\uC5EC\uBD80 :");
		labelUsed.setHorizontalAlignment(SwingConstants.RIGHT);
		labelUsed.setBounds(12, 60, 95, 15);
		contentPanel.add(labelUsed);

		// 사용여부 콤보박스
		comboBoxUsed = new JComboBox<String>();
		comboBoxUsed.setModel(new DefaultComboBoxModel(new String[] {
				"사용함", "사용 안 함" }));
		comboBoxUsed.setSelectedIndex(0);
		comboBoxUsed.setBounds(119, 57, 116, 21);
		contentPanel.add(comboBoxUsed);

		// 확인, 취소버튼 (Default)
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\uD655\uC778");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ok = true;
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\uCDE8\uC18C");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		// 받아온 정보를 필드에 설정
		if (info != null) {
			comboBoxId.setSelectedItem(info.getId());
			comboBoxCCode.setSelectedItem(info.getC_code());
			comboBoxUsed.setSelectedItem(("t".equals(info.getUsed()) ? "사용함" : "사용 안 함"));
		}

		// 창 보여줌.
		setVisible(true);
	}

	/**
	 * 입력한 정보들을 반환. 수정 완료 시 호출
	 * 
	 * @return info
	 */
	public Ownership getInfo() {
		Ownership info = new Ownership();
		
		info.setId((String) comboBoxId.getSelectedItem());
		info.setC_code((String) comboBoxCCode.getSelectedItem());
		info.setUsed(("사용함".equals((String) comboBoxUsed.getSelectedItem()) ? "t" : "f"));
		
		return info;
	}

	/**
	 * 확인, 취소 버튼 눌렀는지 여부 반환
	 * 
	 * @return ok
	 */
	public boolean isOk() {
		return this.ok;
	}
}