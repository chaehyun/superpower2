package Gui;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class CouponPanel extends JPanel {

	private JTable table;
	/**
	 * Create the panel.
	 */
	public CouponPanel() {
		setLayout(null);
		
		// Į�� ����
		Vector<String> columnName = new Vector<String>();
		columnName.add("�����ڵ�");
		columnName.add("��ǰ�ڵ�");
		columnName.add("���η�");
		columnName.add("������");
		columnName.add("������");

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
		scrollPane.setBounds(12, 43, 729, 459);
		add(scrollPane);

		// ���̺�
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		scrollPane.setViewportView(table);
		
		// dummy
		Vector<String> dumm = new Vector<String>();
		dumm.add("32987");
		dumm.add("TRUZHA");
		dumm.add("25");
		dumm.add("2015.04.09");
		dumm.add("2015.04.20");
		
		rowDatas.add(dumm);
	}

}
