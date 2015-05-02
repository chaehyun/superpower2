package Gui;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ProductPanel extends JPanel {

	private JTable table;
	
	/**
	 * Create the panel.
	 */
	public ProductPanel() {
		setLayout(null);

		// Į�� ����
		Vector<String> columnName = new Vector<String>();
		columnName.add("��ǰ�ڵ�");
		columnName.add("��з�");
		columnName.add("�ߺз�");
		columnName.add("�Һз�");
		columnName.add("�Ǹż�");
		columnName.add("����");
		columnName.add("����");
		columnName.add("�̹���");

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
		scrollPane.setViewportView(table);
		
		// dummy
		Vector<String> dumm = new Vector<String>();
		dumm.add("TRUZHA");
		dumm.add("�Ƿ�");
		dumm.add("������");
		dumm.add("ġ��");
		dumm.add("20");
		dumm.add("3");
		dumm.add("19900");
		dumm.add("c\\user");
		
		rowDatas.add(dumm);
	}

}
