package Service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * �̹��� ���� ������.
 * 
 * @author Seongjun
 * @since 2015/5/12
 * @version 2015/5/12
 */
public class ImageManager {

	public static String getStringFromBitmapFile(String filePath) {

		String fileString = null;

		try {
			// ���� ��Ʈ�� ����
			File file = new File(filePath);
			DataInputStream dataInputStream = new DataInputStream(
					new FileInputStream(file));

			// ���� ����
			byte[] bytes = new byte[(int) file.length()];
			dataInputStream.read(bytes, 0, bytes.length);

			// byte array to String
			fileString = new String(bytes);

			// ���� ��Ʈ�� ����
			dataInputStream.close();

		} catch (Exception e) {
			return null;
		}

		return fileString;
	}
}
