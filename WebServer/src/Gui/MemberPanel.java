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
	 * 생성자. Swing 컴포넌트 및 테이블 요소 초기화
	 */
	public MemberPanel() {
		setLayout(null);

		// 새로고침 버튼
		JButton buttonRefresh = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		buttonRefresh.setBounds(12, 10, 97, 23);
		add(buttonRefresh);

		// 추가 버튼
		JButton buttonInsert = new JButton("\uCD94\uAC00");
		buttonInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showAddMember();
			}
		});
		buttonInsert.setBounds(426, 10, 97, 23);
		add(buttonInsert);

		// 수정 버튼
		JButton buttonModify = new JButton("\uC218\uC815");
		buttonModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showModifyMember();
			}
		});
		buttonModify.setEnabled(false);
		buttonModify.setBounds(535, 10, 97, 23);
		add(buttonModify);

		// 삭제 버튼
		JButton buttonDelete = new JButton("\uC0AD\uC81C");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMember();
			}
		});
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(644, 10, 97, 23);
		add(buttonDelete);

		// 칼럼 제목
		Vector<String> columnName = new Vector<String>();
		columnName.add("ID");
		columnName.add("이름");
		columnName.add("성별");
		columnName.add("나이");
		columnName.add("관심사");
		columnName.add("입장수");

		// 행 데이터
		rowDatas = new Vector<Vector>();

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
		dumm.add("한민지");
		dumm.add("F");
		dumm.add("23");
		dumm.add("의류");
		dumm.add("4");

		rowDatas.add(dumm);
	}
	
	/**
	 * 테이블 새로 고침. "새로고침" 버튼에 의해 호출
	 */
	public void refresh() {
		
	}
	
	/**
	 * 회원 추가 창을 띄움. "추가" 버튼에 의해 호출
	 */
	public void showAddMember() {
		
		MemberDialog memberDialog = new MemberDialog();
		
		// 확인 버튼을 누르면 추가 작업
		if(memberDialog.isOk()) {
			
		}
	}
	
	/**
	 * 회원 수정 창을 띄움. "수정" 버튼에 의해 호출
	 */
	public void showModifyMember() {
		
		List<String> infoList = new ArrayList<String>();
		

		//
		// DB로부터 정보를 받아 infoList에 받음 (incomplete)
		//
		
		// 다이얼로그 팝업
		MemberDialog memberDialog = new MemberDialog(infoList);
		
		// 확인 버튼을 누르면 수정 작업
		if(memberDialog.isOk()) {
			
		}		
	}
	
	/**
	 * 확인 여부를 묻고 선택한 회원을 삭제함. "삭제" 버튼에 의해 호출
	 */
	public void deleteMember() {
		int res = JOptionPane.showConfirmDialog(null,"Are you sure?","Delete",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
		
		// 확인을 누르면 회원 삭제
		if(res == 0) {
		}			
	}
}
