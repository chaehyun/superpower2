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

		// 칼럼 제목
		Vector<String> columnName = new Vector<String>();
		columnName.add("상품코드");
		columnName.add("횟수");
		columnName.add("구매일");

		// 행 데이터
		Vector<Vector> rowDatas = new Vector<Vector>();

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
		personalitem.add
		/*
		// dummy
		Vector<String> dumm = new Vector<String>();
		dumm.add("TRUZHA");
		dumm.add("3");
		dumm.add("2015.04.09");

		rowDatas.add(dumm);
		*/
		// 칼럼 제목
		Vector<String> columnName2 = new Vector<String>();
		columnName2.add("쿠폰코드");
		columnName2.add("사용여부");

		// 행 데이터
		Vector<Vector> rowDatas2 = new Vector<Vector>();

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

		// dummy
		Vector<String> dumm2 = new Vector<String>();
		dumm2.add("523423");
		dumm2.add("N");

		rowDatas2.add(dumm2);
	}

}
