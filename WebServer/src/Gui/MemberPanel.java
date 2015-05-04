package Gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MemberPanel extends JPanel {

	private JTable table;
	private Vector<Vector> rowDatas;

	/**
	 * ������. Swing ������Ʈ �� ���̺� ��� �ʱ�ȭ
	 */
	public MemberPanel() {
		setLayout(null);

		// ���ΰ�ħ ��ư
		JButton buttonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		buttonRefresh.setBounds(12, 10, 97, 23);
		add(buttonRefresh);

		// �߰� ��ư
		JButton buttonInsert = new JButton("\uCD94\uAC00");
		buttonInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAddMember();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// ���� ��ư
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// ���� ��ư
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

		// Į�� ����
		Vector<String> columnName = new Vector<String>();
		columnName.add("ID");
		columnName.add("�̸�");
		columnName.add("����");
		columnName.add("����");
		columnName.add("���ɻ�");
		columnName.add("�����");

		// �� ������
		rowDatas = new Vector<Vector>();

		// ���̺� ��
		DefaultTableModel tableModel = new DefaultTableModel(rowDatas,
				columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		// ���̺��� ���� ��ũ���г�
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 43, 729, 459);
		add(scrollPane);

		// ���̺�
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() % 2 == 0) {
					Personal personal = new Personal();
					personal.setVisible(true);
				}
			}
		});
		scrollPane.setViewportView(table);

		// dummy
		Vector<String> dumm = new Vector<String>();
		dumm.add("annie0731");
		dumm.add("�ѹ���");
		dumm.add("F");
		dumm.add("23");
		dumm.add("�Ƿ�");
		dumm.add("4");

		rowDatas.add(dumm);
	}
	
	/**
	 * ȸ�� �߰� â�� ���. "�߰�" ��ư�� ���� ȣ��
	 */
	public void showAddMember() {
		MemberDialog memberDialog = new MemberDialog();
		
		// Ȯ�� ��ư�� ������
		if(memberDialog.isOk()) {
			// ȸ�� �߰��ϴ� �κ� (test)
			for(String info : memberDialog.getInformations()) {
				System.out.println(info);
			}
			// (test)
		}
	}
}
