package Service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 이미지 파일 관리자.
 * 
 * @author Seongjun
 * @since 2015/5/12
 * @version 2015/5/12
 */
public class ImageManager {

	public static String getStringFromBitmapFile(String filePath) {

		String fileString = null;

		try {
			// 파일 스트림 설정
			File file = new File(filePath);
			DataInputStream dataInputStream = new DataInputStream(
					new FileInputStream(file));

			// 파일 읽음
			byte[] bytes = new byte[(int) file.length()];
			dataInputStream.read(bytes, 0, bytes.length);

			// byte array to String
			fileString = new String(bytes);

			// 파일 스트림 닫음
			dataInputStream.close();

		} catch (Exception e) {
			return null;
		}

		return fileString;
	}
}
