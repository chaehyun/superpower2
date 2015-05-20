package Gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Elements.Beacon;

/**
 * ���� �߰�, ������ ���� ���̾�α� Ŭ����
 * 
 * @author Minji, Seongjun
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class BeaconDialog extends JDialog {

	private final JPanel contentPanel = new JPanel(); // Default

	private JTextField textFieldMacAddr; // MAC�ּ� �ʵ�
	private JTextField textFieldLocation; // ��ġ �ʵ�

	private boolean ok = false; // Ȯ��,��� ��ư ����

	/**
	 * ������. ���� �߰� ��ư ���� �� ȣ��
	 */
	public BeaconDialog() {
		this(null);
	}

	/**
	 * ������. ���� ���� ��ư ���� �� ȣ��
	 */
	public BeaconDialog(Beacon info) {

		// ���̾�α� �Ӽ� ����
		setTitle("\uBE44\uCF58 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 262, 123);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane ����
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// MAC�ּ� ���̺�
		JLabel labelMacAddr = new JLabel("MAC \uC8FC\uC18C :");
		labelMacAddr.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMacAddr.setBounds(12, 10, 95, 15);
		contentPanel.add(labelMacAddr);

		// MAC�ּ� �ؽ�Ʈ�ʵ�
		textFieldMacAddr = new JTextField();
		textFieldMacAddr.setBounds(119, 7, 116, 21);
		contentPanel.add(textFieldMacAddr);
		textFieldMacAddr.setColumns(10);

		// ��ġ ���̺�
		JLabel labelLocation = new JLabel("\uC704\uCE58 :");
		labelLocation.setHorizontalAlignment(SwingConstants.RIGHT);
		labelLocation.setBounds(12, 35, 95, 15);
		contentPanel.add(labelLocation);

		// ��ġ �ؽ�Ʈ�ʵ�
		textFieldLocation = new JTextField();
		textFieldLocation.setColumns(10);
		textFieldLocation.setBounds(119, 32, 116, 21);
		contentPanel.add(textFieldLocation);

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
			textFieldMacAddr.setText(info.getMacAddr());
			textFieldLocation.setText(info.getLocation());
		}

		// â ������.
		setVisible(true);
	}

	/**
	 * �Է��� �������� ��ȯ. ���� �Ϸ� �� ȣ��
	 * 
	 * @return info
	 */
	public Beacon getInfo() {

		Beacon info = new Beacon();
		info.setMacAddr(textFieldMacAddr.getText());
		info.setLocation(textFieldLocation.getText());

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