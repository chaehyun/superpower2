package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Database.GetAllMembers;
import Elements.Member;

public class MemberPanel extends JPanel {

	private JTable table;
	private Vector<Vector<String>> rowDatas;

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
		columnName.add("접속여부");

		// 행 데이터
		rowDatas = new Vector<Vector<String>>();

		// 테이블 모델
		DefaultTableModel tableModel = new DefaultTableModel(rowDatas,
				columnName) {
			// 셀 편집 불가능으로 함
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
			// 레코드 더블클릭 시 해당 회원의 정보창을 띄움
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() % 2 == 0) {
					Personal personal = new Personal(rowDatas.get(table.getSelectedRow()).get(0));
					personal.setVisible(true);
				}
			}
		});
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
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
			
			// 기존 테이블 내용 지움
			table.getSelectionModel().clearSelection();
			rowDatas.clear();
			
			// DB로부터 회원 레코드들을 받아서 테이블에 추가
			for(Member member : GetAllMembers.doAction()) {
				Vector<String> row = new Vector<String>();
				row.add(member.getId());
				row.add(member.getName());
				row.add(member.getSex() == 'm' ? "남" : "여");
				row.add(Integer.toString(member.getAge()));
				row.add(member.getFavorite());
				row.add(Integer.toString(member.getEnterCount()));
				row.add(member.getLogFlag() ? "On" : "Off");
				
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
			
		} catch(SQLException e) {
			System.out.println("MemberPanel.refresh()에서 예외 발생 : " + e.getMessage());
		}
	}
	
	/**
	 * 회원 추가 창을 띄움. "추가" 버튼에 의해 호출
	 */
	public void showAddMember() {
		
		MemberDialog memberDialog = new MemberDialog();
		
		// 확인 버튼을 누르면 추가 작업
		if(memberDialog.isOk()) {
			// 미완성
		}
	}
	
	/**
	 * 회원 수정 창을 띄움. "수정" 버튼에 의해 호출
	 */
	public void showModifyMember() {
		
		Member member = new Member();
		

		//
		// DB로부터 정보를 받아 member에 받음 (incomplete)
		//
		
		// 다이얼로그 팝업
		MemberDialog memberDialog = new MemberDialog(member);
		
		// 확인 버튼을 누르면 수정 작업
		if(memberDialog.isOk()) {
			// 미완성
		}		
	}
	
	/**
	 * 확인 여부를 묻고 선택한 회원을 삭제함. "삭제" 버튼에 의해 호출
	 */
	public void deleteMember() {
		int res = JOptionPane.showConfirmDialog(null,"Are you sure?","Delete",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
		
		// 확인을 누르면 회원 삭제
		if(res == 0) {
			// 미완성
		}			
	}
}
