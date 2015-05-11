package Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Elements.Item;

/**
 * DB���� ��ǰ �ϳ� ����. PK�� ���� ��ǰ�ڵ�� ��������� ��ǰ ��ü�� �Ķ���ͷ� ���.
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class ModifyItem {

	/**
	 * @param originCode
	 *            primary key
	 * @param item
	 *            ���� ������
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	synchronized public static void doAction(String originCode, Item item)
			throws SQLException {

		// ����Ŀ�� ��Ȱ��ȭ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "update item set i_code=?, major=?, middle=?, minor=?, sales_volume=?, total_stock=?, price=?, image=? where i_code=?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		pstmt.setString(1, item.geti_code());
		pstmt.setString(2, item.getmajor());
		pstmt.setString(3, item.getmiddle());
		pstmt.setString(4, item.getminor());
		pstmt.setInt(5, item.getsales_volume());
		pstmt.setInt(6, item.gettotal_stock());
		pstmt.setInt(7, item.getprice());
		pstmt.setString(8, item.getimage());
		pstmt.setString(9, originCode);
		pstmt.executeUpdate();

		// Ŀ��
		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);
	}
}
