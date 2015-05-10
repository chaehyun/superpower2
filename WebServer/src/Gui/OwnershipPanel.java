package Gui;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Database.GetAllOwnership;
import Elements.Coupon;
import Elements.Ownership;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OwnershipPanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

	/**
	 * ������. Swing ������Ʈ �� ���̺� ��� �ʱ�ȭ
	 */
	public OwnershipPanel() {
		setLayout(null);

		// "���ΰ�ħ" ��ư
		JButton buttonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		buttonRefresh.setBounds(12, 10, 97, 23);
		add(buttonRefresh);

		// "�߰�" ��ư
		JButton buttonInsert = new JButton("\uCD94\uAC00");
		buttonInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddOwnership();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// "����" ��ư
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModifyOwnership();
			}
		});
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// "����" ��ư
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteOwnership();
			}
		});
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

		// Į�� ����
		Vector<String> columnName = new Vector<String>();
		columnName.add("ȸ��ID");
		columnName.add("�����ڵ�");
		columnName.add("��뿩��");

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

			// DB�� ���� ownership �о�ͼ� �߰�
			for (Ownership ownership : GetAllOwnership.doAction()) {
				Vector<String> row = new Vector<String>();
				row.add(ownership.getId());
				row.add(ownership.getC_code());
				row.add(ownership.getUsed());

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
			System.out.println("OwnershipPanel.refresh()���� ���� �߻� : "
					+ e.getMessage());
		}
	}

	/**
	 * ���� �߰� â�� ���. "�߰�" ��ư�� ���� ȣ��
	 */
	public void showAddOwnership() {

		OwnershipDialog ownershipDialog = new OwnershipDialog();

		// Ȯ�� ��ư�� ������ �߰� �۾�
		if (ownershipDialog.isOk()) {
			// �̿ϼ�
		}
	}

	/**
	 * ���� ���� â�� ���. "����" ��ư�� ���� ȣ��
	 */
	public void showModifyOwnership() {

		Ownership ownership = new Ownership();

		//
		// DB�κ��� ������ �޾� ownership�� ���� (incomplete)
		//

		// ���̾�α� �˾�
		OwnershipDialog ownershipDialog = new OwnershipDialog(ownership);

		// Ȯ�� ��ư�� ������ ���� �۾�
		if (ownershipDialog.isOk()) {
			// �̿ϼ�
		}
	}

	/**
	 * Ȯ�� ���θ� ���� ������ ������ ������. "����" ��ư�� ���� ȣ��
	 */
	public void deleteOwnership() {
		int res = JOptionPane.showConfirmDialog(null, "Are you sure?",
				"Delete", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);

		// Ȯ���� ������ ���� ����
		if (res == 0) {
			// �̿ϼ�
		}
	}
}
