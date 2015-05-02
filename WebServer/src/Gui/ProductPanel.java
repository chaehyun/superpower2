package Gui;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ProductPanel extends JPanel {

	private JTable table;
	
	/**
	 * Create the panel.
	 */
	public ProductPanel() {
		setLayout(null);

		// 칼럼 제목
		Vector<String> columnName = new Vector<String>();
		columnName.add("상품코드");
		columnName.add("대분류");
		columnName.add("중분류");
		columnName.add("소분류");
		columnName.add("판매수");
		columnName.add("재고수");
		columnName.add("가격");
		columnName.add("이미지");

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
		scrollPane.setBounds(12, 10, 729, 492);
		add(scrollPane);

		// 테이블
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		scrollPane.setViewportView(table);
		
		// dummy
		Vector<String> dumm = new Vector<String>();
		dumm.add("TRUZHA");
		dumm.add("의류");
		dumm.add("여성복");
		dumm.add("치마");
		dumm.add("20");
		dumm.add("3");
		dumm.add("19900");
		dumm.add("c\\user");
		
		rowDatas.add(dumm);
	}

}
