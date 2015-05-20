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

import Database.DeleteBeacon;
import Database.GetAllBeacons;
import Database.GetBeacon;
import Database.InsertBeacon;
import Database.ModifyBeacon;
import Elements.Beacon;

/**
 * ���� �߰�, ������ ���� ���̾�α� Ŭ����
 * 
 * @author Seongjun, Minji
 * @since 2015/5/20
 * @version 2015/5/20
 */
public class BeaconPanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

	/**
	 * ������. Swing ������Ʈ �� ���̺� ��� �ʱ�ȭ
	 */
	public BeaconPanel() {
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
				showAddBeacon();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// "����" ��ư
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModifyBeacon();
			}
		});
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// "����" ��ư
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBeacon();
			}
		});
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

		// Į�� ����
		Vector<String> columnName = new Vector<String>();
		columnName.add("MAC �ּ�");
		columnName.add("��ġ");

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

			// DB�� ���� beacon �о�ͼ� �߰�
			for (Beacon beacon : GetAllBeacons.doAction()) {
				Vector<String> row = new Vector<String>();
				row.add(beacon.getMacAddr());
				row.add(beacon.getLocation());

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
			System.out.println("BeaconPanel.refresh()���� ���� �߻� : "
					+ e.getMessage());
		}
	}

	/**
	 * ���� �߰� â�� ���. "�߰�" ��ư�� ���� ȣ��
	 */
	public void showAddBeacon() {

		try {
			// ���̾�α� �˾�
			BeaconDialog beaconDialog = new BeaconDialog();

			// Ȯ�� ��ư�� ������ �߰� �۾�
			if (beaconDialog.isOk()) {
				InsertBeacon.doAction(beaconDialog.getInfo());
			}
		} catch (SQLException e) {
			System.out.println("BeaconPanel.showAddBeacon()���� ���� �߻� "
					+ e.getMessage());
		}

		// ȭ�� ���̺� ���ΰ�ħ
		refresh();
	}

	/**
	 * ���� ���� â�� ���. "����" ��ư�� ���� ȣ��
	 */
	public void showModifyBeacon() {

		try {

			// ���̺����� ���õ� MAC �ּ�
			String selectedMac = rowDatas.get(table.getSelectedRow()).get(0);

			// ���̾�α� �˾�
			BeaconDialog beaconDialog = new BeaconDialog(
					GetBeacon.doAction(selectedMac));

			// Ȯ�� ��ư�� ������ ���� �۾�
			if (beaconDialog.isOk()) {
				ModifyBeacon.doAction(selectedMac, beaconDialog.getInfo());
			}

		} catch (SQLException e) {
			System.out.println("BeaconPanel.showModifyBeacon()���� ���� �߻� "
					+ e.getMessage());
		}

		// ȭ�� ���̺� ���ΰ�ħ
		refresh();
	}

	/**
	 * Ȯ�� ���θ� ���� ������ ������ ������. "����" ��ư�� ���� ȣ��
	 */
	public void deleteBeacon() {
		int res = JOptionPane.showConfirmDialog(null, "Are you sure?",
				"Delete", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);

		// Ȯ���� ������ ���� ����
		if (res == 0) {
			try {
				// ���̺����� ���õ� MAC �ּ�
				String selectedMac = rowDatas.get(table.getSelectedRow())
						.get(0);

				// ���� ����
				DeleteBeacon.doAction(selectedMac);

			} catch (SQLException e) {
				System.out.println("BeaconPanel.deleteBeacon()���� ���� �߻� : "
						+ e.getMessage());
			}
		}

		// ȭ�� ���̺� ���ΰ�ħ
		refresh();
	}
}