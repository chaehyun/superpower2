package Gui;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Personal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table2;

	/**
	 * Create the frame.
	 */
	public Personal() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 634, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		getContentPane().setLayout(null);

		// Į�� ����
		Vector<String> columnName = new Vector<String>();
		columnName.add("��ǰ�ڵ�");
		columnName.add("Ƚ��");
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
		scrollPane.setBounds(12, 10, 293, 365);
		contentPane.add(scrollPane);

		// ���̺�
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		scrollPane.setViewportView(table);

		Vector<String> personalitem = new Vector<String>();
		personalitem.add
		/*
		// dummy
		Vector<String> dumm = new Vector<String>();
		dumm.add("TRUZHA");
		dumm.add("3");
		dumm.add("2015.04.09");

		rowDatas.add(dumm);
		*/
		// Į�� ����
		Vector<String> columnName2 = new Vector<String>();
		columnName2.add("�����ڵ�");
		columnName2.add("��뿩��");

		// �� ������
		Vector<Vector> rowDatas2 = new Vector<Vector>();

		// ���̺� ��
		DefaultTableModel tableModel2 = new DefaultTableModel(rowDatas2,
				columnName2) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// ���̺��� ���� ��ũ���г�
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(317, 10, 289, 365);
		contentPane.add(scrollPane2);

		// ���̺�
		table2 = new JTable(tableModel2);
		table2.getTableHeader().setReorderingAllowed(false);
		table2.getTableHeader().setResizingAllowed(false);
		scrollPane2.setViewportView(table2);

		// dummy
		Vector<String> dumm2 = new Vector<String>();
		dumm2.add("523423");
		dumm2.add("N");

		rowDatas2.add(dumm2);
	}

}
