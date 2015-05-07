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
import Database.InsertCoupon;
import Elements.Coupon;
import Elements.Item;

/**
 * 쿠폰 추가, 수정을 위한 다이얼로그 클래스
 * 
 * @author Minji, Seongjun
 * @since 2015/5/7
 * @version 2015/5/7
 */
public class CouponDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();	// Default
	
	private JTextField textFieldCCode;			// 쿠폰코드 필드
	private JComboBox<String> comboBoxICode;	// 상품코드 콤보박스
	private JSpinner spinnerDiscount;			// 할인율 스피너
	private JTextField textFieldBeginDate;		// 시작일 필드
	private JTextField textFieldEndDate;		// 종료일 필드
	
	private boolean ok = false;	// 확인,취소 버튼 여부	

	/**
	 * 생성자. 회원 추가 버튼 누를 시 호출
	 */
	public CouponDialog() {
		this(null);
	}
	
	/**
	 * 생성자. 회원 수정 버튼 누를 시 호출
	 */
	public CouponDialog(Coupon info) {
		
		// 다이얼로그 속성 설정
		setTitle("\uCFE0\uD3F0 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 262, 199);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane 설정
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// 쿠폰코드 레이블
		JLabel labelCCode = new JLabel("\uCFE0\uD3F0\uCF54\uB4DC :");
		labelCCode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCCode.setBounds(12, 10, 95, 15);
		contentPanel.add(labelCCode);

		// 쿠폰코드 텍스트필드
		textFieldCCode = new JTextField();
		textFieldCCode.setBounds(119, 7, 116, 21);
		contentPanel.add(textFieldCCode);
		textFieldCCode.setColumns(10);

		// 상품코드 레이블
		JLabel labelICode = new JLabel("\uC0C1\uD488\uCF54\uB4DC :");
		labelICode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelICode.setBounds(12, 35, 95, 15);
		contentPanel.add(labelICode);
		
		// 상품코드 콤보박스
		comboBoxICode = new JComboBox<String>();
		try {
			for(Item item : GetAllItems.doAction()) {
				comboBoxICode.addItem(item.geti_code());
			}
		} catch(SQLException e) {
			System.out.println("CouponDialog()에서 예외 발생 : " + e.getMessage());
		}		
		comboBoxICode.setSelectedIndex(0);
		comboBoxICode.setBounds(119, 32, 116, 21);
		contentPanel.add(comboBoxICode);

		// 할인율 레이블
		JLabel labelDiscount = new JLabel("\uD560\uC778\uC728 :");
		labelDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
		labelDiscount.setBounds(12, 60, 95, 15);
		contentPanel.add(labelDiscount);
		
		// 할인율 스피너
		spinnerDiscount = new JSpinner();
		spinnerDiscount.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spinnerDiscount.setBounds(119, 57, 59, 22);
		contentPanel.add(spinnerDiscount);

		// 시작일 레이블
		JLabel labelBeginDate = new JLabel("\uC2DC\uC791\uC77C :");
		labelBeginDate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelBeginDate.setBounds(12, 85, 95, 15);
		contentPanel.add(labelBeginDate);
		
		// 시작일 필드
		textFieldBeginDate = new JTextField();
		textFieldBeginDate.setColumns(10);
		textFieldBeginDate.setBounds(119, 82, 116, 21);
		contentPanel.add(textFieldBeginDate);
		
		// 종료일 레이블
		JLabel labelEndDate = new JLabel("\uC885\uB8CC\uC77C :");
		labelEndDate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelEndDate.setBounds(12, 110, 95, 15);
		contentPanel.add(labelEndDate);
		
		// 종료일 필드
		textFieldEndDate = new JTextField();
		textFieldEndDate.setColumns(10);
		textFieldEndDate.setBounds(119, 107, 116, 21);
		contentPanel.add(textFieldEndDate);
		
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
		if(info != null) {
			textFieldCCode.setText(info.getc_code());
			comboBoxICode.setSelectedItem(info.geti_code());
			spinnerDiscount.setValue(info.getdiscount());
			textFieldBeginDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(info.getbegin_date()));
			textFieldEndDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(info.getend_date()));
		}
		
		// 창 보여줌.
		setVisible(true);
		
		// DB에 insert
		Coupon newcoupon = new Coupon();
		newcoupon = getInfo();
		InsertCoupon.insertcoupon(newcoupon);
	}
	
	/**
	 * 입력한 정보들을 반환. 수정 완료 시 호출
	 * 
	 * @return info
	 */
	public Coupon getInfo() {
		
		Coupon info = new Coupon();
		info.setc_code(textFieldCCode.getText());
		info.seti_code((String) comboBoxICode.getSelectedItem()); 
		info.setdiscount((Integer) spinnerDiscount.getValue());

		try {
			info.setbegin_date(new SimpleDateFormat("yyyy-MM-dd").parse(textFieldBeginDate.getText()));
		} catch (ParseException e) {
			info.setbegin_date(new Date());
		}
		
		try {
			info.setend_date(new SimpleDateFormat("yyyy-MM-dd").parse(textFieldEndDate.getText()));
		} catch (ParseException e) {
			info.setend_date(new Date());
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