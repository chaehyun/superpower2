package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Elements.Ownership;

/**
 * 소유 테이블의 모든 레코드를 받음. 새로고침용
 * 
 * @author Seongjun
 * @since 2015/5/11
 * @version 2015/5/11
 */
public class GetAllOwnership {

	/**
	 * @return 소유 리스트
	 * @throws SQLException
	 *             쿼리 실행 에러시 발생
	 */
	public static List<Ownership> doAction() throws SQLException {

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