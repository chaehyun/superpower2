package Gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * ȸ�� �߰�, ������ ���� ���̾�α� Ŭ����
 * 
 * @author Minji, Seongjun
 * @since 2015/5/4
 * @version 2015/5/4
 */
public class MemberDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JTextField textFieldId;
	private JPasswordField passwordFieldPassword;
	private JTextField textFieldName;
	private JComboBox<String> comboBoxSex;
	private JSpinner spinnerAge;
	private JTextField textFieldFavorite;
	private JSpinner spinnerEnterCount;
	
	private boolean ok = false;

	/**
	 * ������. ȸ�� �߰� ��ư ���� �� ȣ��
	 */
	public MemberDialog() {
		this(null);
	}
	
	/**
	 * ������. ȸ�� ���� ��ư ���� �� ȣ��
	 */
	public MemberDialog(List<String> infoList) {
		
		// ���̾�α� �Ӽ� ����
		setTitle("\uD68C\uC6D0 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 262, 259);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane ����
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// ID ���̺�
		JLabel labelId = new JLabel("ID :");
		labelId.setHorizontalAlignment(SwingConstants.RIGHT);
		labelId.setBounds(12, 10, 95, 15);
		contentPanel.add(labelId);

		// ID �ؽ�Ʈ�ʵ�
		textFieldId = new JTextField();
		textFieldId.setBounds(119, 7, 116, 21);
		contentPanel.add(textFieldId);
		textFieldId.setColumns(10);

		// ��й�ȣ ���̺�
		JLabel labelPassword = new JLabel("\uBE44\uBC00\uBC88\uD638 :");
		labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPassword.setBounds(12, 35, 95, 15);
		contentPanel.add(labelPassword);

		// ��й�ȣ �ʵ�
		passwordFieldPassword = new JPasswordField();
		passwordFieldPassword.setBounds(119, 32, 116, 21);
		contentPanel.add(passwordFieldPassword);

		// �̸� ���̺�
		JLabel labelName = new JLabel("\uC774\uB984 :");
		labelName.setHorizontalAlignment(SwingConstants.RIGHT);
		labelName.setBounds(12, 60, 95, 15);
		contentPanel.add(labelName);

		// �̸� �ʵ�
		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(119, 57, 116, 21);
		contentPanel.add(textFieldName);

		// ���� ���̺�
		JLabel labelSex = new JLabel("\uC131\uBCC4 :");
		labelSex.setHorizontalAlignment(SwingConstants.RIGHT);
		labelSex.setBounds(12, 85, 95, 15);
		contentPanel.add(labelSex);

		// ���� �޺��ڽ�
		comboBoxSex = new JComboBox<String>();
		comboBoxSex.setModel(new DefaultComboBoxModel<String>(new String[] { "\uB0A8",
				"\uC5EC" }));
		comboBoxSex.setSelectedIndex(0);
		comboBoxSex.setBounds(119, 82, 59, 21);
		contentPanel.add(comboBoxSex);
		
		// ���� ���̺�
		JLabel labelAge = new JLabel("\uB098\uC774 :");
		labelAge.setHorizontalAlignment(SwingConstants.RIGHT);
		labelAge.setBounds(12, 110, 95, 15);
		contentPanel.add(labelAge);

		// ���� ���ǳ�
		spinnerAge = new JSpinner();
		spinnerAge.setModel(new SpinnerNumberModel(new Integer(1), new Integer(
				1), null, new Integer(1)));
		spinnerAge.setBounds(119, 107, 59, 22);
		contentPanel.add(spinnerAge);
		
		// ���ɻ� ���̺�
		JLabel labelFavorite = new JLabel("\uAD00\uC2EC\uC0AC :");
		labelFavorite.setHorizontalAlignment(SwingConstants.RIGHT);
		labelFavorite.setBounds(12, 135, 95, 15);
		contentPanel.add(labelFavorite);
		
		// ���ɻ� �ʵ�
		textFieldFavorite = new JTextField();
		textFieldFavorite.setBounds(119, 132, 116, 21);
		contentPanel.add(textFieldFavorite);
		textFieldFavorite.setColumns(10);
		
		// ����� ���̺�
		JLabel labelEnterCount = new JLabel("\uC785\uC7A5\uC218 :");
		labelEnterCount.setHorizontalAlignment(SwingConstants.RIGHT);
		labelEnterCount.setBounds(12, 160, 95, 15);
		contentPanel.add(labelEnterCount);
		
		// ����� ���ǳ�
		spinnerEnterCount = new JSpinner();
		spinnerEnterCount.setModel(new SpinnerNumberModel(new Integer(0),
				new Integer(0), null, new Integer(1)));
		spinnerEnterCount.setBounds(119, 157, 59, 22);
		contentPanel.add(spinnerEnterCount);
		
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
		if(infoList != null) {
			textFieldId.setText(infoList.get(0));
			passwordFieldPassword.setText(infoList.get(1));
			textFieldName.setText(infoList.get(2));
			comboBoxSex.setSelectedItem("m".equals(infoList.get(3))?"��":"��");
			spinnerAge.setValue(Integer.parseInt(infoList.get(4)));
			textFieldFavorite.setText(infoList.get(5));
			spinnerEnterCount.setValue(Integer.parseInt(infoList.get(6)));
		}
		
		// â ������.
		setVisible(true);
	}
	
	/**
	 * �Է��� �������� ��ȯ
	 * 
	 * @return information list
	 */
	public List<String> getInformations() {
		
		List<String> infoList = new ArrayList<String>();
		infoList.add(textFieldId.getText());
		infoList.add(String.valueOf(passwordFieldPassword.getPassword()));
		infoList.add(textFieldName.getText());
		
		String sex = "��".equals((String) comboBoxSex.getSelectedItem())?"m":"f"; 
		infoList.add(sex);
		
		infoList.add(Integer.toString((Integer) spinnerAge.getValue()));
		infoList.add(textFieldFavorite.getText());
		infoList.add(Integer.toString((Integer) spinnerEnterCount.getValue()));		
		
		return infoList;
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
