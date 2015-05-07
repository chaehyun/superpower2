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
 * ���� �߰�, ������ ���� ���̾�α� Ŭ����
 * 
 * @author Minji, Seongjun
 * @since 2015/5/7
 * @version 2015/5/7
 */
public class CouponDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();	// Default
	
	private JTextField textFieldCCode;			// �����ڵ� �ʵ�
	private JComboBox<String> comboBoxICode;	// ��ǰ�ڵ� �޺��ڽ�
	private JSpinner spinnerDiscount;			// ������ ���ǳ�
	private JTextField textFieldBeginDate;		// ������ �ʵ�
	private JTextField textFieldEndDate;		// ������ �ʵ�
	
	private boolean ok = false;	// Ȯ��,��� ��ư ����	

	/**
	 * ������. ȸ�� �߰� ��ư ���� �� ȣ��
	 */
	public CouponDialog() {
		this(null);
	}
	
	/**
	 * ������. ȸ�� ���� ��ư ���� �� ȣ��
	 */
	public CouponDialog(Coupon info) {
		
		// ���̾�α� �Ӽ� ����
		setTitle("\uCFE0\uD3F0 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 262, 199);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane ����
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// �����ڵ� ���̺�
		JLabel labelCCode = new JLabel("\uCFE0\uD3F0\uCF54\uB4DC :");
		labelCCode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCCode.setBounds(12, 10, 95, 15);
		contentPanel.add(labelCCode);

		// �����ڵ� �ؽ�Ʈ�ʵ�
		textFieldCCode = new JTextField();
		textFieldCCode.setBounds(119, 7, 116, 21);
		contentPanel.add(textFieldCCode);
		textFieldCCode.setColumns(10);

		// ��ǰ�ڵ� ���̺�
		JLabel labelICode = new JLabel("\uC0C1\uD488\uCF54\uB4DC :");
		labelICode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelICode.setBounds(12, 35, 95, 15);
		contentPanel.add(labelICode);
		
		// ��ǰ�ڵ� �޺��ڽ�
		comboBoxICode = new JComboBox<String>();
		try {
			for(Item item : GetAllItems.doAction()) {
				comboBoxICode.addItem(item.geti_code());
			}
		} catch(SQLException e) {
			System.out.println("CouponDialog()���� ���� �߻� : " + e.getMessage());
		}		
		comboBoxICode.setSelectedIndex(0);
		comboBoxICode.setBounds(119, 32, 116, 21);
		contentPanel.add(comboBoxICode);

		// ������ ���̺�
		JLabel labelDiscount = new JLabel("\uD560\uC778\uC728 :");
		labelDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
		labelDiscount.setBounds(12, 60, 95, 15);
		contentPanel.add(labelDiscount);
		
		// ������ ���ǳ�
		spinnerDiscount = new JSpinner();
		spinnerDiscount.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spinnerDiscount.setBounds(119, 57, 59, 22);
		contentPanel.add(spinnerDiscount);

		// ������ ���̺�
		JLabel labelBeginDate = new JLabel("\uC2DC\uC791\uC77C :");
		labelBeginDate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelBeginDate.setBounds(12, 85, 95, 15);
		contentPanel.add(labelBeginDate);
		
		// ������ �ʵ�
		textFieldBeginDate = new JTextField();
		textFieldBeginDate.setColumns(10);
		textFieldBeginDate.setBounds(119, 82, 116, 21);
		contentPanel.add(textFieldBeginDate);
		
		// ������ ���̺�
		JLabel labelEndDate = new JLabel("\uC885\uB8CC\uC77C :");
		labelEndDate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelEndDate.setBounds(12, 110, 95, 15);
		contentPanel.add(labelEndDate);
		
		// ������ �ʵ�
		textFieldEndDate = new JTextField();
		textFieldEndDate.setColumns(10);
		textFieldEndDate.setBounds(119, 107, 116, 21);
		contentPanel.add(textFieldEndDate);
		
		// Ȯ��, ��ҹ�ư (Default)
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
		
		// �޾ƿ� ������ �ʵ忡 ����		
		if(info != null) {
			textFieldCCode.setText(info.getc_code());
			comboBoxICode.setSelectedItem(info.geti_code());
			spinnerDiscount.setValue(info.getdiscount());
			textFieldBeginDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(info.getbegin_date()));
			textFieldEndDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(info.getend_date()));
		}
		
		// â ������.
		setVisible(true);
		
		// DB�� insert
		Coupon newcoupon = new Coupon();
		newcoupon = getInfo();
		InsertCoupon.insertcoupon(newcoupon);
	}
	
	/**
	 * �Է��� �������� ��ȯ. ���� �Ϸ� �� ȣ��
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
	 * Ȯ��, ��� ��ư �������� ���� ��ȯ
	 * 
	 * @return ok
	 */
	public boolean isOk() {
		return this.ok;
	}
}