package web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HtmlUtils {

	public static void DownloadHtml(String url, File outputFile) {
		InputStream src;
		FileOutputStream out;
		File temp;
		byte[] readBuffer = new byte[255];

		try {
			src = new URL(url).openStream();
			temp = File.createTempFile("temp", "temp");
			out = new FileOutputStream(temp);
			while (src.read(readBuffer) > 0) {
				out.write(readBuffer);
			}
			src.close();
			out.close();
			out = new FileOutputStream(outputFile);
			src = new FileInputStream(temp);
			readBuffer = new byte[(int) temp.length()];
			src.read(readBuffer);
			out.write(readBuffer);
			src.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * File temp = src; while(true){ temp = temp.getParentFile();
	 * if(!temp.exists()){ if(temp.equals(src)){ try { src.createNewFile();
	 * return; } catch (Exception e) { e.printStackTrace(); } } temp.mkdir(); }
	 * }
	 */

	

}
