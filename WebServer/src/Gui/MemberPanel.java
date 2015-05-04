package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class MemberPanel extends JPanel {

	private JTable table;
	private Vector<Vector> rowDatas;

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

		// �� ������
		rowDatas = new Vector<Vector>();

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
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() % 2 == 0) {
					Personal personal = new Personal();
					personal.setVisible(true);
				}
			}
		});
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {				
				boolean isSelected = table.getSelectedRowCount() > 0;
				buttonModify.setEnabled(isSelected);
				buttonDelete.setEnabled(isSelected);				
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
	
	/**
	 * ���̺� ���� ��ħ. "���ΰ�ħ" ��ư�� ���� ȣ��
	 */
	public void refresh() {
		
	}
	
	/**
	 * ȸ�� �߰� â�� ���. "�߰�" ��ư�� ���� ȣ��
	 */
	public void showAddMember() {
		
		MemberDialog memberDialog = new MemberDialog();
		
		// Ȯ�� ��ư�� ������ �߰� �۾�
		if(memberDialog.isOk()) {
			
		}
	}
	
	/**
	 * ȸ�� ���� â�� ���. "����" ��ư�� ���� ȣ��
	 */
	public void showModifyMember() {
		
		List<String> infoList = new ArrayList<String>();
		

		//
		// DB�κ��� ������ �޾� infoList�� ���� (incomplete)
		//
		
		// ���̾�α� �˾�
		MemberDialog memberDialog = new MemberDialog(infoList);
		
		// Ȯ�� ��ư�� ������ ���� �۾�
		if(memberDialog.isOk()) {
			
		}		
	}
	
	/**
	 * Ȯ�� ���θ� ���� ������ ȸ���� ������. "����" ��ư�� ���� ȣ��
	 */
	public void deleteMember() {
		int res = JOptionPane.showConfirmDialog(null,"Are you sure?","Delete",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
		
		// Ȯ���� ������ ȸ�� ����
		if(res == 0) {
		}			
	}
}
