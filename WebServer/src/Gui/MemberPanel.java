package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Database.GetAllMembers;
import Elements.Member;

public class MemberPanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

	/**
	 * ������. Swing ������Ʈ �� ���̺� ��� �ʱ�ȭ
	 */
	public MemberPanel() {
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
			public void actionPerformed(ActionEvent arg0) {
				showAddMember();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// ���� ��ư
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModifyMember();
			}
		});
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// ���� ��ư
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMember();
			}
		});
		buttonDelete.setEnabled(false);
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
		columnName.add("���ӿ���");

		// �� ������
		rowDatas = new Vector<Vector<String>>();

		// ���̺� ��
		DefaultTableModel tableModel = new DefaultTableModel(rowDatas,
				columnName) {
			// �� ���� �Ұ������� ��
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
			// ���ڵ� ����Ŭ�� �� �ش� ȸ���� ����â�� ���
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() % 2 == 0) {
					Personal personal = new Personal(rowDatas.get(table.getSelectedRow()).get(0));
					personal.setVisible(true);
				}
			}
		});
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
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
			
			// ���� ���̺� ���� ����
			table.getSelectionModel().clearSelection();
			rowDatas.clear();
			
			// DB�κ��� ȸ�� ���ڵ���� �޾Ƽ� ���̺� �߰�
			for(Member member : GetAllMembers.doAction()) {
				Vector<String> row = new Vector<String>();
				row.add(member.getId());
				row.add(member.getName());
				row.add(member.getSex() == 'm' ? "��" : "��");
				row.add(Integer.toString(member.getAge()));
				row.add(member.getFavorite());
				row.add(Integer.toString(member.getEnterCount()));
				row.add(member.getLogFlag() ? "On" : "Off");
				
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
			
		} catch(SQLException e) {
			System.out.println("MemberPanel.refresh()���� ���� �߻� : " + e.getMessage());
		}
	}
	
	/**
	 * ȸ�� �߰� â�� ���. "�߰�" ��ư�� ���� ȣ��
	 */
	public void showAddMember() {
		
		MemberDialog memberDialog = new MemberDialog();
		
		// Ȯ�� ��ư�� ������ �߰� �۾�
		if(memberDialog.isOk()) {
			// �̿ϼ�
		}
	}
	
	/**
	 * ȸ�� ���� â�� ���. "����" ��ư�� ���� ȣ��
	 */
	public void showModifyMember() {
		
		Member member = new Member();
		

		//
		// DB�κ��� ������ �޾� member�� ���� (incomplete)
		//
		
		// ���̾�α� �˾�
		MemberDialog memberDialog = new MemberDialog(member);
		
		// Ȯ�� ��ư�� ������ ���� �۾�
		if(memberDialog.isOk()) {
			// �̿ϼ�
		}		
	}
	
	/**
	 * Ȯ�� ���θ� ���� ������ ȸ���� ������. "����" ��ư�� ���� ȣ��
	 */
	public void deleteMember() {
		int res = JOptionPane.showConfirmDialog(null,"Are you sure?","Delete",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
		
		// Ȯ���� ������ ȸ�� ����
		if(res == 0) {
			// �̿ϼ�
		}			
	}
}
