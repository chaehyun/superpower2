package Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

/**
 * 이미지 파일 관리자.
 * 
 * @author Seongjun
 * @since 2015/5/12
 * @version 2015/5/12
 */
public class ImageManager {

	/**
	 * 이미지파일을 바이트로 변환후, 이것을 또 스트링으로 변환하여 반환.
	 * 
	 * @param filePath
	 *            이미지파일 경로
	 * @return 이미지 스트링
	 */
	public static String getStringFromImageFile(String filePath) {

		String fileString = null;

		try {
			// 파일 스트림 설정
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);

			// 파일 읽음
			byte[] bytes = new byte[(int) file.length()];
			fis.read(bytes);

			// byte array to String
			fileString = Base64.getEncoder().encodeToString(bytes);

			// 파일 스트림 닫음
			fis.close();

		} catch (Exception e) {
			return null;
		}

		return fileString;
	}
}
