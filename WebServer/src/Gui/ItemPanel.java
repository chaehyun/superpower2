package Gui;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import Database.GetAllCoupons;
import Database.GetAllItems;
import Elements.Coupon;
import Elements.Item;

public class ItemPanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

	/**
	 * 생성자. Swing 컴포넌트 및 테이블 요소 초기화
	 */
	public ItemPanel() {
		setLayout(null);
		
		// 새로고침 버튼
		JButton buttonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		buttonRefresh.setBounds(12, 10, 97, 23);
		add(buttonRefresh);
		
		// 추가 버튼
		JButton buttonInsert = new JButton("\uCD94\uAC00");
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);
		
		// 수정 버튼
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);
		
		// 삭제 버튼
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

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
		rowDatas = new Vector<Vector<String>>();

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
		
		refresh();
	}
	
	public void refresh(){
		try{
			// 기존 테이블 clear
			table.getSelectionModel().clearSelection();
			rowDatas.clear();
			
			// DB로 부터 coupon 읽어와서 추가
			for(Item item : GetAllItems.doAction()){
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
			
			// 각 열을 가운데 정렬
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			for(int i=0;i<table.getColumnCount();i++) {				
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
						
			// 테이블 그림 새로고침
			table.setVisible(false);
			table.setVisible(true);
			
		}catch(SQLException e){
			System.out.println("ItemPanel.refresh()에서 예외 발생 : " + e.getMessage());
		}
	}
	
}
