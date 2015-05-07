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
import Elements.Coupon;
import Elements.Item;

/**
 * ��ǰ �߰�, ������ ���� ���̾�α� Ŭ����
 * 
 * @author Minji, Seongjun
 * @since 2015/5/7
 * @version 2015/5/7
 */
public class ItemDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();	// Default
	
	private JTextField textFieldICode;		// ��ǰ�ڵ� �ʵ�
	private JTextField textFieldMajor;		// ��з� �ʵ�
	private JTextField textFieldMiddle;		// �ߺз� �ʵ�
	private JTextField textFieldMinor;		// �Һз� �ʵ�
	private JSpinner spinnerSalesVolume;	// �Ǹż� ���ǳ�
	private JSpinner spinnerTotalStock;		// ���� ���ǳ�
	private JSpinner spinnerPrice;			// ���� ���ǳ�
	private JTextField textFieldImage;		// �̹��� �ʵ�
	
	private boolean ok = false;	// Ȯ��,��� ��ư ����	

	/**
	 * ������. ȸ�� �߰� ��ư ���� �� ȣ��
	 */
	public ItemDialog() {
		this(null);
	}
	
	/**
	 * ������. ȸ�� ���� ��ư ���� �� ȣ��
	 */
	public ItemDialog(Item info) {
		
		// ���̾�α� �Ӽ� ����
		setTitle("\uC0C1\uD488 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 385, 280);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane ����
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// ��ǰ�ڵ� ���̺�
		JLabel labelICode = new JLabel("\uCFE0\uD3F0\uCF54\uB4DC :");
		labelICode.setHorizontalAlignment(SwingConstants.RIGHT);
		labelICode.setBounds(12, 10, 95, 15);
		contentPanel.add(labelICode);

		// ��ǰ�ڵ� �ؽ�Ʈ�ʵ�
		textFieldICode = new JTextField();
		textFieldICode.setBounds(119, 7, 116, 21);
		textFieldICode.setColumns(10);
		contentPanel.add(textFieldICode);

		// ��з� ���̺�
		JLabel labelMajor = new JLabel("\uB300\uBD84\uB958 :");
		labelMajor.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMajor.setBounds(12, 35, 95, 15);
		contentPanel.add(labelMajor);

		// ��з� �ؽ�Ʈ�ʵ�
		textFieldMajor = new JTextField();
		textFieldMajor.setColumns(10);
		textFieldMajor.setBounds(119, 32, 116, 21);
		contentPanel.add(textFieldMajor);

		// �ߺз� ���̺�
		JLabel labelMiddle = new JLabel("\uC911\uBD84\uB958 :");
		labelMiddle.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMiddle.setBounds(12, 60, 95, 15);
		contentPanel.add(labelMiddle);

		// �ߺз� �ؽ�Ʈ�ʵ�
		textFieldMiddle = new JTextField();
		textFieldMiddle.setColumns(10);
		textFieldMiddle.setBounds(119, 57, 116, 21);
		contentPanel.add(textFieldMiddle);

		// �Һз� ���̺�
		JLabel labelMinor = new JLabel("\uC18C\uBD84\uB958 :");
		labelMinor.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMinor.setBounds(12, 85, 95, 15);
		contentPanel.add(labelMinor);
		
		// �Һз� �ؽ�Ʈ�ʵ�
		textFieldMinor = new JTextField();
		textFieldMinor.setColumns(10);
		textFieldMinor.setBounds(119, 82, 116, 21);
		contentPanel.add(textFieldMinor);
		
		// �Ǹż� ���̺�
		JLabel labelSalesVolume = new JLabel("\uD310\uB9E4\uC218 :");
		labelSalesVolume.setHorizontalAlignment(SwingConstants.RIGHT);
		labelSalesVolume.setBounds(12, 110, 95, 15);
		contentPanel.add(labelSalesVolume);
		
		// �Ǹż� ���ǳ�
		spinnerSalesVolume = new JSpinner();
		spinnerSalesVolume.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerSalesVolume.setBounds(119, 107, 59, 22);
		contentPanel.add(spinnerSalesVolume);
		
		// ���� ���̺�
		JLabel labelTotalStock = new JLabel("\uC7AC\uACE0\uC218 :");
		labelTotalStock.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalStock.setBounds(12, 135, 95, 15);
		contentPanel.add(labelTotalStock);
		
		// ���� ���ǳ�
		spinnerTotalStock = new JSpinner();
		spinnerTotalStock.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerTotalStock.setBounds(119, 132, 59, 22);
		contentPanel.add(spinnerTotalStock);
		
		// ���� ���̺�
		JLabel labelPrice = new JLabel("\uAC00\uACA9 :");
		labelPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPrice.setBounds(12, 160, 95, 15);
		contentPanel.add(labelPrice);
		
		// ���� ���ǳ�
		spinnerPrice = new JSpinner();
		spinnerPrice.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(10)));
		spinnerPrice.setBounds(119, 157, 116, 22);
		contentPanel.add(spinnerPrice);
		
		// �̹��� ���̺�
		JLabel labelImage = new JLabel("\uC774\uBBF8\uC9C0\uACBD\uB85C :");
		labelImage.setHorizontalAlignment(SwingConstants.RIGHT);
		labelImage.setBounds(12, 185, 95, 15);
		contentPanel.add(labelImage);
		
		// �̹��� �ؽ�Ʈ�ʵ�
		textFieldImage = new JTextField();
		textFieldImage.setColumns(10);
		textFieldImage.setBounds(119, 182, 248, 21);
		contentPanel.add(textFieldImage);
		
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
			textFieldICode.setText(info.geti_code());
			textFieldMajor.setText(info.getmajor());
			textFieldMiddle.setText(info.getmiddle());
			textFieldMinor.setText(info.getminor());
			spinnerSalesVolume.setValue(info.getsales_volume());
			spinnerTotalStock.setValue(info.gettotal_stock());
			spinnerPrice.setValue(info.getprice());
			textFieldImage.setText(info.getimage());
		}
		
		// â ������.
		setVisible(true);
	}
	
	/**
	 * �Է��� �������� ��ȯ. ���� �Ϸ� �� ȣ��
	 * 
	 * @return info
	 */
	public Item getInfo() {		
		Item info = new Item();
		info.seti_code(textFieldICode.getText());
		info.setmajor(textFieldMajor.getText());
		info.setmiddle(textFieldMiddle.getText());
		info.setminor(textFieldMinor.getText());
		info.setsales_volume((Integer) spinnerSalesVolume.getValue());
		info.settotal_stock((Integer) spinnerTotalStock.getValue());
		info.setprice((Integer) spinnerPrice.getValue());
		info.setimage(textFieldImage.getText());
		
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