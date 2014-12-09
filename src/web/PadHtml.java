package web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream.GetField;

import Database.database;

public abstract class PadHtml {

	static FileInputStream in;
	static FileOutputStream out;
	static int padLenth = 0, index = 0;
	static byte[] data;
	static boolean suposedToPad, inSection, inPar = false;

	public static void padHtml(File src) {

		try {
			in = new FileInputStream(src);
			data = new byte[(int) src.length()];
			in.read(data);
			in.close();
			src.delete();
			src.createNewFile();
			boolean inQuotations = false;
			out = new FileOutputStream(src);
			for (byte ss : data) {
				out.write(ss);
					if (ss == '"' && !inPar)
						inPar = true;
					else if (ss == '"' && inPar) {
						inPar = false;
					}
					if (!inPar) {
						if (ss == '>') {
							suposedToPad = true;
						} else if (Value((byte) '<', index + 1)
								&& Value((byte) '/', index + 2)) {
							suposedToPad = true;
							padLenth--;
						}

						if (ss == '<' && !Value((byte) '/', index + 1)) {
							padLenth++;
						}

						if (suposedToPad) {
							out.write((byte) 0x0a);
							// out.write(pad((byte) 0x09, padLenth));
							suposedToPad = false;
						}

					}
				
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// tab = 0x09

	private static byte[] pad(byte s, int with) {
		byte[] ret = new byte[with];
		for (int z = 0; z < with; z++) {
			ret[z] = (byte) s;
		}
		return ret;
	}

	private static boolean Value(byte comp, int x) {
		if (index + 1 < data.length)
			if (data[x] == comp)
				return true;

		return false;
	}
}
