package Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

/**
 * �̹��� ���� ������.
 * 
 * @author Seongjun
 * @since 2015/5/12
 * @version 2015/5/12
 */
public class ImageManager {

	/**
	 * �̹��������� ����Ʈ�� ��ȯ��, �̰��� �� ��Ʈ������ ��ȯ�Ͽ� ��ȯ.
	 * 
	 * @param filePath
	 *            �̹������� ���
	 * @return �̹��� ��Ʈ��
	 */
	public static String getStringFromImageFile(String filePath) {

		String fileString = null;

		try {
			// ���� ��Ʈ�� ����
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);

			// ���� ����
			byte[] bytes = new byte[(int) file.length()];
			fis.read(bytes);

			// byte array to String
			fileString = Base64.getEncoder().encodeToString(bytes);

			// ���� ��Ʈ�� ����
			fis.close();

		} catch (Exception e) {
			return null;
		}

		return fileString;
	}
}
