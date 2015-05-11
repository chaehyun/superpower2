package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Database.DeleteItem;
import Database.GetAllItems;
import Database.GetItem;
import Database.InsertItem;
import Database.ModifyItem;
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
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		buttonRefresh.setBounds(12, 10, 97, 23);
		add(buttonRefresh);

		// �߰� ��ư
		JButton buttonInsert = new JButton("\uCD94\uAC00");
		buttonInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddItem();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// ���� ��ư
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModifyItem();
			}
		});
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// ���� ��ư
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteItem();
			}
		});
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
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					// ���ڵ� �ϳ� ���� �� "����", "����" ��ư Ȱ��ȭ
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						boolean isSelected = table.getSelectedRowCount() > 0;
						buttonModify.setEnabled(isSelected);
						buttonDelete.setEnabled(isSelected);
					}
				});
		scrollPane.setViewportView(table);

		// ��� ���� �� ���̺� ���ΰ�ħ
		refresh();
	}

	/**
	 * ���̺� ���� ��ħ. "���ΰ�ħ" ��ư�� ���� ȣ��
	 */
	public void refresh() {
		try {
			// ���� ���̺� clear
			table.getSelectionModel().clearSelection();
			rowDatas.clear();

			// DB�� ���� item �о�ͼ� �߰�
			for (Item item : GetAllItems.doAction()) {
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
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}

			// ���̺� �׸� ���ΰ�ħ
			table.setVisible(false);
			table.setVisible(true);

		} catch (SQLException e) {
			System.out.println("ItemPanel.refresh()���� ���� �߻� : "
					+ e.getMessage());
		}
	}

	/**
	 * ��ǰ �߰� â�� ���. "�߰�" ��ư�� ���� ȣ��
	 */
	public void showAddItem() {

		try {
			// ���̾�α� �˾�
			ItemDialog itemDialog = new ItemDialog();

			// Ȯ�� ��ư�� ������ �߰� �۾�
			if (itemDialog.isOk()) {
				InsertItem.doAction(itemDialog.getInfo());
			}
		} catch (SQLException e) {
			System.out.println("ItemPanel.showAddItem()���� ���� �߻� "
					+ e.getMessage());
		}

		// ȭ�� ���̺� ���ΰ�ħ
		refresh();
	}

	/**
	 * ��ǰ ���� â�� ���. "����" ��ư�� ���� ȣ��
	 */
	public void showModifyItem() {

		try {

			// ���̺��� ���õ� ��ǰ�ڵ�
			String selectedCode = rowDatas.get(table.getSelectedRow()).get(0);

			// ���̾�α� �˾�
			ItemDialog itemDialog = new ItemDialog(
					GetItem.doAction(selectedCode));

			// Ȯ�� ��ư�� ������ ���� �۾�
			if (itemDialog.isOk()) {
				ModifyItem.doAction(selectedCode, itemDialog.getInfo());
			}
		} catch (SQLException e) {
			System.out.println("ItemPanel.showModifyItem()���� ���� �߻� "
					+ e.getMessage());
		}

		// ȭ�� ���̺� ���ΰ�ħ
		refresh();
	}

	/**
	 * Ȯ�� ���θ� ���� ������ ��ǰ�� ������. "����" ��ư�� ���� ȣ��
	 */
	public void deleteItem() {
		int res = JOptionPane.showConfirmDialog(null, "Are you sure?",
				"Delete", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);

		// Ȯ���� ������ ��ǰ ����
		if (res == 0) {
			try {
				// ���̺��� ���õ� ��ǰ�ڵ�
				String selectedCode = rowDatas.get(table.getSelectedRow()).get(
						0);

				// ���� ����
				DeleteItem.doAction(selectedCode);

			} catch (SQLException e) {
				System.out.println("ItemPanel.deleteItem()���� ���� �߻� : "
						+ e.getMessage());
			}
		}

		// ȭ�� ���̺� ���ΰ�ħ
		refresh();
	}
}
