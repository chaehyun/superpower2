package Gui;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import Database.GetAllCoupons;
import Database.GetAllItems;
import Elements.Coupon;
import Elements.Item;

public class ItemPanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

	/**
	 * ������. Swing ������Ʈ �� ���̺� ��� �ʱ�ȭ
	 */
	public ItemPanel() {
		setLayout(null);
		
		// ���ΰ�ħ ��ư
		JButton buttonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		buttonRefresh.setBounds(12, 10, 97, 23);
		add(buttonRefresh);
		
		// �߰� ��ư
		JButton buttonInsert = new JButton("\uCD94\uAC00");
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);
		
		// ���� ��ư
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);
		
		// ���� ��ư
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

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
		rowDatas = new Vector<Vector<String>>();

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
		
		refresh();
	}
	
	public void refresh(){
		try{
			// ���� ���̺� clear
			table.getSelectionModel().clearSelection();
			rowDatas.clear();
			
			// DB�� ���� coupon �о�ͼ� �߰�
			for(Item item : GetAllItems.doAction()){
				Vector<String> row = new Vector<String>();
				row.add(item.geti_code());
				row.add(item.getmajor());
				row.add(item.getmiddle());
				row.add(item.getminor());
				row.add(Integer.toString(item.getsales_volume()));
				row.add(Integer.toString(item.gettotal_stock()));
				row.add(Integer.toString(item.getprice()));
				row.add(item.getimage());
	
				rowDatas.add(row);
			}
			
			// �� ���� ��� ����
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			for(int i=0;i<table.getColumnCount();i++) {				
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
						
			// ���̺� �׸� ���ΰ�ħ
			table.setVisible(false);
			table.setVisible(true);
			
		}catch(SQLException e){
			System.out.println("ItemPanel.refresh()���� ���� �߻� : " + e.getMessage());
		}
	}
	
}
