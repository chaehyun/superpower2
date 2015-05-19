package Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Database.DbConnector;
import Database.GetPersonalItem;
import Database.Getmajorid;
import Elements.PersonalFavorite;
import Elements.Purchase;

public class UpdateFavorite {

	public static String givecoupon(String userid) throws SQLException {

		String major;
		int purchase_count;
		
		List<PersonalFavorite> personalfavorite = new ArrayList<PersonalFavorite>();
		List<Purchase> personalpurchaselist = new ArrayList<Purchase>();
		personalpurchaselist = GetPersonalItem.doAction(userid);

		// PersonalFavorite�ʱ�ȭ
		InitPersonalfavorite(personalfavorite);

		for (int i = 0; i < personalpurchaselist.size(); i++) {
			// ��з� �޾ƿ� I_code�� param���� �ؼ� getmajor�� ����
			// major �� �޾ƿ�
			major = Getmajorid
					.getmajor(personalpurchaselist.get(i).getI_code());
			// ���� Ƚ�� �޾ƿ�
			purchase_count = personalpurchaselist.get(i).getCount();
			System.out.println(major);

			// major id�� count ������Ŵ
			for (int j = 0; j < 6; j++) {

				if (personalfavorite.get(j).getMajor().equals(major)) {

					int count = personalfavorite.get(j).getCount();
					personalfavorite.get(j).setCount(count + (1*purchase_count));
					System.out.println(personalfavorite.get(j).getMajor()
							+ personalfavorite.get(j).getCount());

				}
			}
		}

		Collections.sort(personalfavorite, new Comparefavorite());
		major = personalfavorite.get(0).getMajor();

		// DB�� ������Ʈ
		DbConnector.getInstance().getConnection().setAutoCommit(false);

		// ���� ����
		String sql = "update member set favorite = ? where id = ?";
		PreparedStatement pstmt = DbConnector.getInstance().getConnection()
				.prepareStatement(sql);

		pstmt.setString(1, major);
		pstmt.setString(2, userid);

		pstmt.executeUpdate();

		DbConnector.getInstance().getConnection().commit();
		DbConnector.getInstance().getConnection().setAutoCommit(true);

		return major;

	}

	@SuppressWarnings("null")
	public static void InitPersonalfavorite(
			List<PersonalFavorite> personalfavorite) {

		PersonalFavorite initpersonal = new PersonalFavorite();

		initpersonal.setMajor("beauty");
		initpersonal.setCount(0);
		personalfavorite.add(initpersonal);

		initpersonal = new PersonalFavorite();
		initpersonal.setMajor("grocery");
		initpersonal.setCount(0);
		personalfavorite.add(initpersonal);

		initpersonal = new PersonalFavorite();
		initpersonal.setMajor("electronic");
		initpersonal.setCount(0);
		personalfavorite.add(initpersonal);

		initpersonal = new PersonalFavorite();
		initpersonal.setMajor("food");
		initpersonal.setCount(0);
		personalfavorite.add(initpersonal);

		initpersonal = new PersonalFavorite();
		initpersonal.setMajor("apperal");
		initpersonal.setCount(0);
		personalfavorite.add(initpersonal);

		initpersonal = new PersonalFavorite();
		initpersonal.setMajor("drink");
		initpersonal.setCount(0);
		personalfavorite.add(initpersonal);
	}
}

class Comparefavorite implements Comparator<PersonalFavorite> {

	public int compare(PersonalFavorite arg0, PersonalFavorite arg1) {
		return arg0.getCount() > arg1.getCount() ? -1 : arg0.getCount() < arg1
				.getCount() ? 1 : 0;
	}
}
