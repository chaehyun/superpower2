package Gui;

import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Database.GetPersonalCoupon;
import Database.GetPersonalItem;
import Elements.Ownership;
import Elements.Purchase;

public class Personal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table2;
	private Vector<Vector> rowDatas = new Vector<Vector>();
	private Vector<Vector> rowDatas2 = new Vector<Vector>();

	public Personal(String userid) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 634, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		getContentPane().setLayout(null);

		// 칼럼 제목
		Vector<String> columnName = new Vector<String>();
		columnName.add("상품코드");
		columnName.add("횟수");
		columnName.add("구매일");

		// 테이블 모델
		DefaultTableModel tableModel = new DefaultTableModel(rowDatas,
				columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// 테이블을 담을 스크롤패널
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 293, 365);
		contentPane.add(scrollPane);

		// 테이블
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		scrollPane.setViewportView(table);

		Vector<String> personalitem = new Vector<String>();

		getpersonalpurchase(userid);

		// 칼럼 제목
		Vector<String> columnName2 = new Vector<String>();
		columnName2.add("쿠폰코드");
		columnName2.add("사용여부");

		// 테이블 모델
		DefaultTableModel tableModel2 = new DefaultTableModel(rowDatas2,
				columnName2) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// 테이블을 담을 스크롤패널
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(317, 10, 289, 365);
		contentPane.add(scrollPane2);

		// 테이블
		table2 = new JTable(tableModel2);
		table2.getTableHeader().setReorderingAllowed(false);
		table2.getTableHeader().setResizingAllowed(false);
		scrollPane2.setViewportView(table2);

		getpersonalcoupon(userid);
	}

	public void getpersonalpurchase(String userid) {
		
		// 기존 테이블 clear
		table.getSelectionModel().clearSelection();
		rowDatas.clear();

		for (Purchase purchase : GetPersonalItem.doAction(userid)) {
			Vector<String> row = new Vector<String>();
			row.add(purchase.getI_code());
			row.add(Integer.toString(purchase.getCount()));
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			row.add(date.format(purchase.getPur_date()));
			rowDatas.add(row);
		}

		// 각 열을 가운데 정렬
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		// 테이블 그림 새로고침
		table.setVisible(false);
		table.setVisible(true);
	}

	public void getpersonalcoupon(String userid) {
		// 기존 테이블 clear
		table2.getSelectionModel().clearSelection();
		rowDatas2.clear();

		for (Ownership ownership : GetPersonalCoupon.doAction(userid)) {
			Vector<String> row = new Vector<String>();
			row.add(ownership.getC_code());
			row.add(ownership.getUsed());

			rowDatas2.add(row);
		}

		// 각 열을 가운데 정렬
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table2.getColumnCount(); i++) {
			table2.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		// 테이블 그림 새로고침
		table2.setVisible(false);
		table2.setVisible(true);
	}
}
