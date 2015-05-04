package Gui;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class CouponPanel extends JPanel {

	private JTable table;
	/**
	 * Create the panel.
	 */
	public CouponPanel() {
		setLayout(null);
		
		// 칼럼 제목
		Vector<String> columnName = new Vector<String>();
		columnName.add("쿠폰코드");
		columnName.add("상품코드");
		columnName.add("할인률");
		columnName.add("시작일");
		columnName.add("종료일");

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
		scrollPane.setBounds(12, 43, 729, 459);
		add(scrollPane);

		// 테이블
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		scrollPane.setViewportView(table);
		
		// dummy
		Vector<String> dumm = new Vector<String>();
		dumm.add("32987");
		dumm.add("TRUZHA");
		dumm.add("25");
		dumm.add("2015.04.09");
		dumm.add("2015.04.20");
		
		rowDatas.add(dumm);
	}

}
