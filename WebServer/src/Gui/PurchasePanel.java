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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Database.GetAllPurchase;
import Elements.Ownership;
import Elements.Purchase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PurchasePanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

	/**
	 * 생성자. Swing 컴포넌트 및 테이블 요소 초기화
	 */
	public PurchasePanel() {
		setLayout(null);

		// "새로고침" 버튼
		JButton buttonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		buttonRefresh.setBounds(12, 10, 97, 23);
		add(buttonRefresh);

		// "추가" 버튼
		JButton buttonInsert = new JButton("\uCD94\uAC00");
		buttonInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddPurchase();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// "수정" 버튼
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModifyPurchase();
			}
		});
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// "삭제" 버튼
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletePurchase();
			}
		});
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

		// 칼럼 제목
		Vector<String> columnName = new Vector<String>();
		columnName.add("회원ID");
		columnName.add("상품코드");
		columnName.add("수량");
		columnName.add("구매일");

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
			
			// DB로 부터 purchase 읽어와서 추가
			for(Purchase purchase : GetAllPurchase.doAction()){
				Vector<String> row = new Vector<String>();
				row.add(purchase.getId());
				row.add(purchase.getI_code());
				row.add(Integer.toString(purchase.getCount()));
				
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				
				row.add(date.format(purchase.getPur_date()));
	
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
			System.out.println("PurchasePanel.refresh()에서 예외 발생 : " + e.getMessage());
		}
	}

	/**
	 * 구매 추가 창을 띄움. "추가" 버튼에 의해 호출
	 */
	public void showAddPurchase() {

		PurchaseDialog purchaseDialog = new PurchaseDialog();

		// 확인 버튼을 누르면 추가 작업
		if (purchaseDialog.isOk()) {
			// 미완성
		}
	}

	/**
	 * 구매 수정 창을 띄움. "수정" 버튼에 의해 호출
	 */
	public void showModifyPurchase() {

		Purchase purchase = new Purchase();

		//
		// DB로부터 정보를 받아 purchase에 받음 (incomplete)
		//

		// 다이얼로그 팝업
		PurchaseDialog ownershipDialog = new PurchaseDialog(purchase);

		// 확인 버튼을 누르면 수정 작업
		if (ownershipDialog.isOk()) {
			// 미완성
		}
	}

	/**
	 * 확인 여부를 묻고 선택한 구매를 삭제함. "삭제" 버튼에 의해 호출
	 */
	public void deletePurchase() {
		int res = JOptionPane.showConfirmDialog(null, "Are you sure?",
				"Delete", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);

		// 확인을 누르면 쿠폰 삭제
		if (res == 0) {
			// 미완성
		}
	}
}
