package web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import DataTypes.ArrayConventions.genericStack;

public class HtmlUtils {

	public static class HTMLGrouping {
		private String name;
		private String description;
		private boolean selfClosing = false;

		genericStack<HTMLGrouping> attbr = new genericStack<HTMLGrouping>();

		public HTMLGrouping(String Tag) {
			try {
				if (Tag.startsWith("#")) {
					this.setName(Tag.substring(Tag.indexOf("#") + 1,
							Tag.indexOf("~")));
					setDescription(Tag.substring(Tag.indexOf("~") + 1,
							Tag.indexOf("|")));
					selfClosing = Tag.substring(Tag.lastIndexOf("|") + 1)
							.equals("true");
				} else if (Tag.startsWith("!")) {
					this.setName(Tag.substring(Tag.indexOf("!") + 1,
							Tag.indexOf("~")));
					setDescription(Tag.substring(Tag.indexOf("~") + 1));
				}
			} catch (Exception e) {
			}
		}

		public boolean isSelfClosing() {
			return selfClosing;
		}

		public void addattbr(HTMLGrouping s) {
			attbr.add(s);
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

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
