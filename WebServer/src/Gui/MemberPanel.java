package Gui;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MemberPanel extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public MemberPanel() {
		setLayout(null);

		// 칼럼 제목
		Vector<String> columnName = new Vector<String>();
		columnName.add("ID");
		columnName.add("이름");
		columnName.add("성별");
		columnName.add("나이");
		columnName.add("관심사");
		columnName.add("입장수");

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
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() %2 == 0) {
					Personal personal = new Personal();
					personal.setVisible(true);
				}
			}
		});
		scrollPane.setViewportView(table);
		
		// dummy
		Vector<String> dumm = new Vector<String>();
		dumm.add("annie0731");
		dumm.add("한민지");
		dumm.add("F");
		dumm.add("23");
		dumm.add("의류");
		dumm.add("4");
		
		rowDatas.add(dumm);

	}
}
