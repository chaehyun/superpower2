package Gui;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Database.GetAllPurchase;
import Elements.Ownership;
import Elements.Purchase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PurchasePanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

	/**
	 * ������. Swing ������Ʈ �� ���̺� ��� �ʱ�ȭ
	 */
	public PurchasePanel() {
		setLayout(null);

		// "���ΰ�ħ" ��ư
		JButton buttonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		buttonRefresh.setBounds(12, 10, 97, 23);
		add(buttonRefresh);

		// "�߰�" ��ư
		JButton buttonInsert = new JButton("\uCD94\uAC00");
		buttonInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddPurchase();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// "����" ��ư
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModifyPurchase();
			}
		});
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// "����" ��ư
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletePurchase();
			}
		});
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

		// Į�� ����
		Vector<String> columnName = new Vector<String>();
		columnName.add("ȸ��ID");
		columnName.add("��ǰ�ڵ�");
		columnName.add("����");
		columnName.add("������");

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
			
			// DB�� ���� purchase �о�ͼ� �߰�
			for(Purchase purchase : GetAllPurchase.doAction()){
				Vector<String> row = new Vector<String>();
				row.add(purchase.getId());
				row.add(purchase.getI_code());
				row.add(Integer.toString(purchase.getCount()));
				
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				
				row.add(date.format(purchase.getPur_date()));
	
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
			System.out.println("PurchasePanel.refresh()���� ���� �߻� : " + e.getMessage());
		}
	}

	/**
	 * ���� �߰� â�� ���. "�߰�" ��ư�� ���� ȣ��
	 */
	public void showAddPurchase() {

		PurchaseDialog purchaseDialog = new PurchaseDialog();

		// Ȯ�� ��ư�� ������ �߰� �۾�
		if (purchaseDialog.isOk()) {
			// �̿ϼ�
		}
	}

	/**
	 * ���� ���� â�� ���. "����" ��ư�� ���� ȣ��
	 */
	public void showModifyPurchase() {

		Purchase purchase = new Purchase();

		//
		// DB�κ��� ������ �޾� purchase�� ���� (incomplete)
		//

		// ���̾�α� �˾�
		PurchaseDialog ownershipDialog = new PurchaseDialog(purchase);

		// Ȯ�� ��ư�� ������ ���� �۾�
		if (ownershipDialog.isOk()) {
			// �̿ϼ�
		}
	}

	/**
	 * Ȯ�� ���θ� ���� ������ ���Ÿ� ������. "����" ��ư�� ���� ȣ��
	 */
	public void deletePurchase() {
		int res = JOptionPane.showConfirmDialog(null, "Are you sure?",
				"Delete", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);

		// Ȯ���� ������ ���� ����
		if (res == 0) {
			// �̿ϼ�
		}
	}
}
