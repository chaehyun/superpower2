package Gui;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MemberPanel extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public MemberPanel() {
		setLayout(null);

		// Į�� ����
		Vector<String> columnName = new Vector<String>();
		columnName.add("ID");
		columnName.add("�̸�");
		columnName.add("����");
		columnName.add("����");
		columnName.add("���ɻ�");
		columnName.add("�����");

		// �� ������
		Vector<Vector> rowDatas = new Vector<Vector>();

		// ���̺� ��
		DefaultTableModel tableModel = new DefaultTableModel(rowDatas,
				columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// ���̺��� ���� ��ũ���г�
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 729, 492);
		add(scrollPane);

		// ���̺�
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() %2 == 0) {
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
}
