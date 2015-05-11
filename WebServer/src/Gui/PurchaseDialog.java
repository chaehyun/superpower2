package Gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Database.GetAllItems;
import Database.GetAllMembers;
import Elements.Item;
import Elements.Member;
import Elements.Purchase;

/**
 * 구매내역 추가, 수정을 위한 다이얼로그 클래스
 * 
 * @author Minji, Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class PurchaseDialog extends JDialog {

	private final JPanel contentPanel = new JPanel(); // Default

	private JTextField textFieldCode; // 쿠폰코드 필드
	private JComboBox<String> comboBoxId; // 아이디 콤보박스
	private JComboBox<String> comboBoxICode; // 상품코드 콤보박스
	private JSpinner spinnerCount; // 구매수 스피너
	private JTextField textFieldDate; // 날짜 필드

	private boolean ok = false; // 확인,취소 버튼 여부

	/**
	 * 생성자. 구매 추가 버튼 누를 시 호출
	 */
	public PurchaseDialog() {
		this(null);
	}

	/**
	 * 생성자. 구매 수정 버튼 누를 시 호출
	 */
	public PurchaseDialog(Purchase info) {

		// 다이얼로그 속성 설정
		setTitle("\uAD6C\uB9E4 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 262, 196);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane 설정
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// 쿠폰코드 레이블
		JLabel labelCode = new JLabel("\uCFE0\uD3F0\uCF54\uB4DC :");
		labelCode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCode.setBounds(12, 10, 95, 15);
		contentPanel.add(labelCode);

		// 쿠폰코드 필드
		textFieldCode = new JTextField();
		textFieldCode.setColumns(10);
		textFieldCode.setBounds(119, 7, 116, 21);
		contentPanel.add(textFieldCode);

		// 아이디 레이블
		JLabel labelId = new JLabel("ID :");
		labelId.setHorizontalAlignment(SwingConstants.RIGHT);
		labelId.setBounds(12, 35, 95, 15);
		contentPanel.add(labelId);

		// 아이디 콤보박스
		comboBoxId = new JComboBox<String>();
		try {
			for (Member member : GetAllMembers.doAction()) {
				comboBoxId.addItem(member.getId());
			}
		} catch (SQLException e) {
			System.out.println("PurchaseDialog()에서 예외 발생 : " + e.getMessage());
		}
		comboBoxId.setSelectedIndex(0);
		comboBoxId.setBounds(119, 32, 116, 21);
		contentPanel.add(comboBoxId);

		// 상품코드 레이블
		JLabel labelICode = new JLabel("\uC0C1\uD488\uCF54\uB4DC :");
		labelICode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelICode.setBounds(12, 60, 95, 15);
		contentPanel.add(labelICode);

		// 상품코드 콤보박스
		comboBoxICode = new JComboBox<String>();
		try {
			for (Item item : GetAllItems.doAction()) {
				comboBoxICode.addItem(item.geti_code());
			}
		} catch (SQLException e) {
			System.out.println("PurchaseDialog()에서 예외 발생 : " + e.getMessage());
		}
		comboBoxICode.setSelectedIndex(0);
		comboBoxICode.setBounds(119, 57, 116, 21);
		contentPanel.add(comboBoxICode);

		// 구매수 레이블
		JLabel labelCount = new JLabel("\uAD6C\uB9E4\uC218 :");
		labelCount.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCount.setBounds(12, 85, 95, 15);
		contentPanel.add(labelCount);

		// 구매수 스피너
		spinnerCount = new JSpinner();
		spinnerCount.setModel(new SpinnerNumberModel(new Integer(0),
				new Integer(0), null, new Integer(1)));
		spinnerCount.setBounds(119, 82, 59, 22);
		contentPanel.add(spinnerCount);

		// 날짜 레이블
		JLabel labelDate = new JLabel("\uB0A0\uC9DC :");
		labelDate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelDate.setBounds(12, 110, 95, 15);
		contentPanel.add(labelDate);

		// 날짜 필드
		textFieldDate = new JTextField();
		textFieldDate.setColumns(10);
		textFieldDate.setBounds(119, 107, 116, 21);
		contentPanel.add(textFieldDate);

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
			textFieldCode.setText(info.getP_code());
			comboBoxId.setSelectedItem(info.getId());
			comboBoxICode.setSelectedItem(info.getI_code());
			spinnerCount.setValue((Integer) info.getCount());
			textFieldDate.setText(new SimpleDateFormat("yyyy-MM-dd")
					.format(info.getPur_date()));
		}

		// 창 보여줌.
		setVisible(true);
	}

	/**
	 * 입력한 정보들을 반환. 수정 완료 시 호출
	 * 
	 * @return info
	 */
	public Purchase getInfo() {
		Purchase info = new Purchase();
		info.setP_code(textFieldCode.getText());
		info.setId((String) comboBoxId.getSelectedItem());
		info.setI_code((String) comboBoxICode.getSelectedItem());
		info.setCount((Integer) spinnerCount.getValue());

		try {
			info.setPur_date(new SimpleDateFormat("yyyy-MM-dd")
					.parse(textFieldDate.getText()));
		} catch (ParseException e) {
			info.setPur_date(new Date());
		}

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