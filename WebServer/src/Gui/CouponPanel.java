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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Database.GetAllCoupons;
import Elements.Coupon;
import Elements.Member;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CouponPanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

	/**
	 * ������. Swing ������Ʈ �� ���̺� ��� �ʱ�ȭ
	 */
	public CouponPanel() {
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
				showAddCoupon();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// "����" ��ư
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModifyCoupon();
			}
		});
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// "����" ��ư
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCoupon();
			}
		});
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

		// Į�� ����
		Vector<String> columnName = new Vector<String>();
		columnName.add("�����ڵ�");
		columnName.add("��ǰ�ڵ�");
		columnName.add("���η�");
		columnName.add("������");
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
		
		refresh();
	}
	
	/**
	 * ���̺� ���� ��ħ. "���ΰ�ħ" ��ư�� ���� ȣ��
	 */
	public void refresh(){
		try{
			// ���� ���̺� clear
			table.getSelectionModel().clearSelection();
			rowDatas.clear();
			
			// DB�� ���� coupon �о�ͼ� �߰�
			for(Coupon coupon : GetAllCoupons.doAction()){
				Vector<String> row = new Vector<String>();
				row.add(coupon.getc_code());
				row.add(coupon.geti_code());
				row.add(Integer.toString(coupon.getdiscount()));				
				
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				
				row.add(date.format(coupon.getbegin_date()));
				row.add(date.format(coupon.getend_date()));
				
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
			System.out.println("CouponPanel.refresh()���� ���� �߻� : " + e.getMessage());
		}
	}

	
	/**
	 * ���� �߰� â�� ���. "�߰�" ��ư�� ���� ȣ��
	 */
	public void showAddCoupon() {
		
		CouponDialog couponDialog = new CouponDialog();
		
		// Ȯ�� ��ư�� ������ �߰� �۾�
		if(couponDialog.isOk()) {
			// �̿ϼ�
		}
	}

	/**
	 * ���� ���� â�� ���. "����" ��ư�� ���� ȣ��
	 */
	public void showModifyCoupon() {
		
		Coupon coupon = new Coupon();		

		//
		// DB�κ��� ������ �޾� coupon�� ���� (incomplete)
		//
		
		// ���̾�α� �˾�
		CouponDialog couponDialog = new CouponDialog(coupon);
		
		// Ȯ�� ��ư�� ������ ���� �۾�
		if(couponDialog.isOk()) {
			// �̿ϼ�
		}		
	}
	
	/**
	 * Ȯ�� ���θ� ���� ������ ȸ���� ������. "����" ��ư�� ���� ȣ��
	 */
	public void deleteCoupon() {
		int res = JOptionPane.showConfirmDialog(null,"Are you sure?","Delete",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
		
		// Ȯ���� ������ ȸ�� ����
		if(res == 0) {
			// �̿ϼ�
		}			
	}
}
