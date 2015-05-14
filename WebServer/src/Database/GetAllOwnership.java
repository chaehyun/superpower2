package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Ownership;

/**
 * ���� ���̺��� ��� ���ڵ带 ����. ���ΰ�ħ��
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetAllOwnership {

	/**
	 * @return ���� ����Ʈ
	 * @throws SQLException
	 *             ���� ���� ������ �߻�
	 */
	public static List<Ownership> doAction() throws SQLException {

		// ���� ����
		String sql = "select * from ownership";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// ������� ArrayList�� ����
		List<Ownership> ownershipList = new ArrayList<Ownership>();

		while (resultSet.next()) {
			Ownership ownership = new Ownership();
			ownership.setId(resultSet.getString("id"));
			ownership.setC_code(resultSet.getString("c_code"));
			ownership.setUsed(resultSet.getString("used"));
			ownershipList.add(ownership);
		}

		return ownershipList;
	}
}