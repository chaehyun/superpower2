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
 * ���ų��� �߰�, ������ ���� ���̾�α� Ŭ����
 * 
 * @author Minji, Seongjun
 * @since 2015/5/10
 * @version 2015/5/10
 */
public class PurchaseDialog extends JDialog {

	private final JPanel contentPanel = new JPanel(); // Default

	private JTextField textFieldCode; // �����ڵ� �ʵ�
	private JComboBox<String> comboBoxId; // ���̵� �޺��ڽ�
	private JComboBox<String> comboBoxICode; // ��ǰ�ڵ� �޺��ڽ�
	private JSpinner spinnerCount; // ���ż� ���ǳ�
	private JTextField textFieldDate; // ��¥ �ʵ�

	private boolean ok = false; // Ȯ��,��� ��ư ����

	/**
	 * ������. ���� �߰� ��ư ���� �� ȣ��
	 */
	public PurchaseDialog() {
		this(null);
	}

	/**
	 * ������. ���� ���� ��ư ���� �� ȣ��
	 */
	public PurchaseDialog(Purchase info) {

		// ���̾�α� �Ӽ� ����
		setTitle("\uAD6C\uB9E4 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 262, 196);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane ����
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// �����ڵ� ���̺�
		JLabel labelCode = new JLabel("\uCFE0\uD3F0\uCF54\uB4DC :");
		labelCode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCode.setBounds(12, 10, 95, 15);
		contentPanel.add(labelCode);

		// �����ڵ� �ʵ�
		textFieldCode = new JTextField();
		textFieldCode.setColumns(10);
		textFieldCode.setBounds(119, 7, 116, 21);
		contentPanel.add(textFieldCode);

		// ���̵� ���̺�
		JLabel labelId = new JLabel("ID :");
		labelId.setHorizontalAlignment(SwingConstants.RIGHT);
		labelId.setBounds(12, 35, 95, 15);
		contentPanel.add(labelId);

		// ���̵� �޺��ڽ�
		comboBoxId = new JComboBox<String>();
		try {
			for (Member member : GetAllMembers.doAction()) {
				comboBoxId.addItem(member.getId());
			}
		} catch (SQLException e) {
			System.out.println("PurchaseDialog()���� ���� �߻� : " + e.getMessage());
		}
		comboBoxId.setSelectedIndex(0);
		comboBoxId.setBounds(119, 32, 116, 21);
		contentPanel.add(comboBoxId);

		// ��ǰ�ڵ� ���̺�
		JLabel labelICode = new JLabel("\uC0C1\uD488\uCF54\uB4DC :");
		labelICode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelICode.setBounds(12, 60, 95, 15);
		contentPanel.add(labelICode);

		// ��ǰ�ڵ� �޺��ڽ�
		comboBoxICode = new JComboBox<String>();
		try {
			for (Item item : GetAllItems.doAction()) {
				comboBoxICode.addItem(item.geti_code());
			}
		} catch (SQLException e) {
			System.out.println("PurchaseDialog()���� ���� �߻� : " + e.getMessage());
		}
		comboBoxICode.setSelectedIndex(0);
		comboBoxICode.setBounds(119, 57, 116, 21);
		contentPanel.add(comboBoxICode);

		// ���ż� ���̺�
		JLabel labelCount = new JLabel("\uAD6C\uB9E4\uC218 :");
		labelCount.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCount.setBounds(12, 85, 95, 15);
		contentPanel.add(labelCount);

		// ���ż� ���ǳ�
		spinnerCount = new JSpinner();
		spinnerCount.setModel(new SpinnerNumberModel(new Integer(0),
				new Integer(0), null, new Integer(1)));
		spinnerCount.setBounds(119, 82, 59, 22);
		contentPanel.add(spinnerCount);

		// ��¥ ���̺�
		JLabel labelDate = new JLabel("\uB0A0\uC9DC :");
		labelDate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelDate.setBounds(12, 110, 95, 15);
		contentPanel.add(labelDate);

		// ��¥ �ʵ�
		textFieldDate = new JTextField();
		textFieldDate.setColumns(10);
		textFieldDate.setBounds(119, 107, 116, 21);
		contentPanel.add(textFieldDate);

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
		if (info != null) {
			textFieldCode.setText(info.getP_code());
			comboBoxId.setSelectedItem(info.getId());
			comboBoxICode.setSelectedItem(info.getI_code());
			spinnerCount.setValue((Integer) info.getCount());
			textFieldDate.setText(new SimpleDateFormat("yyyy-MM-dd")
					.format(info.getPur_date()));
		}

		// â ������.
		setVisible(true);
	}

	/**
	 * �Է��� �������� ��ȯ. ���� �Ϸ� �� ȣ��
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
	 * Ȯ��, ��� ��ư �������� ���� ��ȯ
	 * 
	 * @return ok
	 */
	public boolean isOk() {
		return this.ok;
	}
}