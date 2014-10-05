package Files;

import java.io.File;

public class CustomFileCreator extends CustomFiles {

	private CustomFileCreator(String fileName, byte[] header) throws Exception {
		super(fileName, header);
	}

	public static CustomFiles CreateNewFile(String fileName, byte[] header) {
		try {
			return new CustomFileCreator(fileName, header);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
