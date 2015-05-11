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

import Database.DeleteOwnership;
import Database.GetAllOwnership;
import Database.GetOwnership;
import Database.InsertOwnership;
import Database.ModifyOwnership;
import Elements.Ownership;

public class OwnershipPanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

	/**
	 * 생성자. Swing 컴포넌트 및 테이블 요소 초기화
	 */
	public OwnershipPanel() {
		setLayout(null);

		// "새로고침" 버튼
		JButton buttonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		buttonRefresh.setBounds(12, 10, 97, 23);
		add(buttonRefresh);

		// "추가" 버튼
		JButton buttonInsert = new JButton("\uCD94\uAC00");
		buttonInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddOwnership();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// "수정" 버튼
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModifyOwnership();
			}
		});
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// "삭제" 버튼
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteOwnership();
			}
		});
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

		// 칼럼 제목
		Vector<String> columnName = new Vector<String>();
		columnName.add("회원ID");
		columnName.add("쿠폰코드");
		columnName.add("사용여부");

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
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					// 레코드 하나 선택 시 "수정", "삭제" 버튼 활성화
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						boolean isSelected = table.getSelectedRowCount() > 0;
						buttonModify.setEnabled(isSelected);
						buttonDelete.setEnabled(isSelected);
					}
				});
		scrollPane.setViewportView(table);

		// 모두 설정 후 테이블 새로고침
		refresh();
	}

	/**
	 * 테이블 새로 고침. "새로고침" 버튼에 의해 호출
	 */
	public void refresh() {
		try {
			// 기존 테이블 clear
			table.getSelectionModel().clearSelection();
			rowDatas.clear();

			// DB로 부터 ownership 읽어와서 추가
			for (Ownership ownership : GetAllOwnership.doAction()) {
				Vector<String> row = new Vector<String>();
				row.add(ownership.getId());
				row.add(ownership.getC_code());
				row.add("t".equals(ownership.getUsed()) ? "사용함" : "사용 안 함");

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

		} catch (SQLException e) {
			System.out.println("OwnershipPanel.refresh()에서 예외 발생 : "
					+ e.getMessage());
		}
	}

	/**
	 * 소유 추가 창을 띄움. "추가" 버튼에 의해 호출
	 */
	public void showAddOwnership() {

		try {
			// 다이얼로그 팝업
			OwnershipDialog ownershipDialog = new OwnershipDialog();

			// 확인 버튼을 누르면 추가 작업
			if (ownershipDialog.isOk()) {
				InsertOwnership.doAction(ownershipDialog.getInfo());
			}
		} catch (SQLException e) {
			System.out.println("OwnershipPanel.showAddOwnership()에서 예외 발생 "
					+ e.getMessage());
		}

		// 화면 테이블 새로고침
		refresh();
	}

	/**
	 * 소유 수정 창을 띄움. "수정" 버튼에 의해 호출
	 */
	public void showModifyOwnership() {

		try {

			// 테이블에서 선택된 회원ID와 쿠폰코드
			String selectedId = rowDatas.get(table.getSelectedRow()).get(0);
			String selectedCode = rowDatas.get(table.getSelectedRow()).get(1);

			// 다이얼로그 팝업
			OwnershipDialog ownershipDialog = new OwnershipDialog(
					GetOwnership.doAction(selectedId, selectedCode));

			// 확인 버튼을 누르면 수정 작업
			if (ownershipDialog.isOk()) {
				ModifyOwnership.doAction(selectedId, selectedCode,
						ownershipDialog.getInfo());
			}
		} catch (SQLException e) {
			System.out.println("OwnershipPanel.showModifyOwnership()에서 예외 발생 "
					+ e.getMessage());
		}

		// 화면 테이블 새로고침
		refresh();
	}

	/**
	 * 확인 여부를 묻고 선택한 소유을 삭제함. "삭제" 버튼에 의해 호출
	 */
	public void deleteOwnership() {
		int res = JOptionPane.showConfirmDialog(null, "Are you sure?",
				"Delete", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);

		// 확인을 누르면 소유 삭제
		if (res == 0) {
			try {
				// 테이블에서 선택된 회원ID와 쿠폰코드
				String selectedId = rowDatas.get(table.getSelectedRow()).get(0);
				String selectedCode = rowDatas.get(table.getSelectedRow()).get(
						1);

				// 삭제 수행
				DeleteOwnership.doAction(selectedId, selectedCode);

			} catch (SQLException e) {
				System.out
						.println("OwnershipPanel.deleteOwnership()에서 예외 발생 : "
								+ e.getMessage());
			}
		}

		// 화면 테이블 새로고침
		refresh();
	}
}
