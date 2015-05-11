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
 * ���� �߰�, ������ ���� ���̾�α� Ŭ����
 * 
 * @author Minji, Seongjun
 * @since 2015/5/7
 * @version 2015/5/7
 */
public class OwnershipDialog extends JDialog {

	private final JPanel contentPanel = new JPanel(); // Default

	private JComboBox<String> comboBoxId; 		// ���̵� �޺��ڽ�
	private JComboBox<String> comboBoxCCode; 	// �����ڵ� �޺��ڽ�
	private JComboBox<String> comboBoxUsed;		

	private boolean ok = false; // Ȯ��,��� ��ư ����

	/**
	 * ������. ���� �߰� ��ư ���� �� ȣ��
	 */
	public OwnershipDialog() {
		this(null);
	}

	/**
	 * ������. ���� ���� ��ư ���� �� ȣ��
	 */
	public OwnershipDialog(Ownership info) {

		// ���̾�α� �Ӽ� ����
		setTitle("\uC18C\uC720 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 262, 148);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane ����
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// ���̵� ���̺�
		JLabel labelId = new JLabel("ID :");
		labelId.setHorizontalAlignment(SwingConstants.RIGHT);
		labelId.setBounds(12, 10, 95, 15);
		contentPanel.add(labelId);

		// ���̵� �޺��ڽ�
		comboBoxId = new JComboBox<String>();
		try {
			for (Member member : GetAllMembers.doAction()) {
				comboBoxId.addItem(member.getId());
			}
		} catch (SQLException e) {
			System.out.println("OwnershipDialog()���� ���� �߻� : " + e.getMessage());
		}
		comboBoxId.setSelectedIndex(0);
		comboBoxId.setBounds(119, 7, 116, 21);
		contentPanel.add(comboBoxId);

		// �����ڵ� ���̺�
		JLabel labelCCode = new JLabel("\uCFE0\uD3F0\uCF54\uB4DC :");
		labelCCode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCCode.setBounds(12, 35, 95, 15);
		contentPanel.add(labelCCode);

		// �����ڵ� �޺��ڽ�
		comboBoxCCode = new JComboBox<String>();
		try {
			for (Coupon coupon : GetAllCoupons.doAction()) {
				comboBoxCCode.addItem(coupon.getc_code());
			}
		} catch (SQLException e) {
			System.out.println("OwnershipDialog()���� ���� �߻� : " + e.getMessage());
		}
		comboBoxCCode.setSelectedIndex(0);
		comboBoxCCode.setBounds(119, 32, 116, 21);
		contentPanel.add(comboBoxCCode);

		// ��뿩�� ���̺�
		JLabel labelUsed = new JLabel("\uC0AC\uC6A9\uC5EC\uBD80 :");
		labelUsed.setHorizontalAlignment(SwingConstants.RIGHT);
		labelUsed.setBounds(12, 60, 95, 15);
		contentPanel.add(labelUsed);

		// ��뿩�� �޺��ڽ�
		comboBoxUsed = new JComboBox<String>();
		comboBoxUsed.setModel(new DefaultComboBoxModel(new String[] {
				"�����", "��� �� ��" }));
		comboBoxUsed.setSelectedIndex(0);
		comboBoxUsed.setBounds(119, 57, 116, 21);
		contentPanel.add(comboBoxUsed);

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
			comboBoxId.setSelectedItem(info.getId());
			comboBoxCCode.setSelectedItem(info.getC_code());
			comboBoxUsed.setSelectedItem(("t".equals(info.getUsed()) ? "�����" : "��� �� ��"));
		}

		// â ������.
		setVisible(true);
	}

	/**
	 * �Է��� �������� ��ȯ. ���� �Ϸ� �� ȣ��
	 * 
	 * @return info
	 */
	public Ownership getInfo() {
		Ownership info = new Ownership();
		
		info.setId((String) comboBoxId.getSelectedItem());
		info.setC_code((String) comboBoxCCode.getSelectedItem());
		info.setUsed(("�����".equals((String) comboBoxUsed.getSelectedItem()) ? "t" : "f"));
		
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