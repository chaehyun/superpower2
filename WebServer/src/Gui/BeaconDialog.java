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
 * 비콘 추가, 수정을 위한 다이얼로그 클래스
 * 
 * @author Minji, Seongjun
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class BeaconDialog extends JDialog {

	private final JPanel contentPanel = new JPanel(); // Default

	private JTextField textFieldMacAddr; // MAC주소 필드
	private JTextField textFieldLocation; // 위치 필드

	private boolean ok = false; // 확인,취소 버튼 여부

	/**
	 * 생성자. 비콘 추가 버튼 누를 시 호출
	 */
	public BeaconDialog() {
		this(null);
	}

	/**
	 * 생성자. 비콘 수정 버튼 누를 시 호출
	 */
	public BeaconDialog(Beacon info) {

		// 다이얼로그 속성 설정
		setTitle("\uBE44\uCF58 \uAD00\uB9AC");
		setModal(true);
		setBounds(0, 0, 262, 123);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ContentPane 설정
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// MAC주소 레이블
		JLabel labelMacAddr = new JLabel("MAC \uC8FC\uC18C :");
		labelMacAddr.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMacAddr.setBounds(12, 10, 95, 15);
		contentPanel.add(labelMacAddr);

		// MAC주소 텍스트필드
		textFieldMacAddr = new JTextField();
		textFieldMacAddr.setBounds(119, 7, 116, 21);
		contentPanel.add(textFieldMacAddr);
		textFieldMacAddr.setColumns(10);

		// 위치 레이블
		JLabel labelLocation = new JLabel("\uC704\uCE58 :");
		labelLocation.setHorizontalAlignment(SwingConstants.RIGHT);
		labelLocation.setBounds(12, 35, 95, 15);
		contentPanel.add(labelLocation);

		// 위치 텍스트필드
		textFieldLocation = new JTextField();
		textFieldLocation.setColumns(10);
		textFieldLocation.setBounds(119, 32, 116, 21);
		contentPanel.add(textFieldLocation);

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
			textFieldMacAddr.setText(info.getMacAddr());
			textFieldLocation.setText(info.getLocation());
		}

		// 창 보여줌.
		setVisible(true);
	}

	/**
	 * 입력한 정보들을 반환. 수정 완료 시 호출
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
	 * 확인, 취소 버튼 눌렀는지 여부 반환
	 * 
	 * @return ok
	 */
	public boolean isOk() {
		return this.ok;
	}
}