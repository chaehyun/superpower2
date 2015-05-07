package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Ownership;

public class GetAllOwnership {
	synchronized public static List<Ownership> doAction() throws SQLException {

		// 쿼리 실행
		String sql = "select * from ownership";
		PreparedStatement statement = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		// 결과들을 ArrayList에 저장
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
