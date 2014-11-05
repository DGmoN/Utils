package Text;

import java.awt.Rectangle;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import DataTypes.ByteConventions;
import DataTypes.ByteConventions.byteSegement;
import Files.CustomFileCreator;
import Files.CustomFiles;
import Files.SymsFile;
import Formating.Strings;
import Formating.Strings.LINES;

public abstract class Fonting {

	private static class Raster {
		ByteBuffer bb;

		private static byte x;

		final byte CharID;

		final int width, height;

		int y = 0;
		int perLine;

		public Raster(int w, int h) {
			CharID = x++;
			width = w;
			height = h;
			bb = ByteBuffer.allocate((w / 8) * h);
		}

		private void put(String a) {
			String Work;
			if (y < height) {
				if (a.length() != 0) {
					if (a.length() < width) {
						Work = autoFill(width, a);
					} else
						Work = a;
					String[] lines = separate(8, Work);
					for (String Y : lines) {
						bb.put(binStringToByte(Y));
					}
				} else {
					bb.put(binStringToByte(autoFill(width, "")));

				}
				y++;
			}
		}

		public byte[] getData() {
			return bb.array();
		}

		public int getLine() {
			return y;
		}

		public String[] separate(int sectionSize, String a) {
			String Work = "";
			System.out.println("[RASTER][SEPARATE] : SECTION SIZE = "
					+ sectionSize);
			System.out.println("[RASTER][SEPARATE] : STRING SIZE = "
					+ a.length());
			Work = a;
			String[] ret = new String[(Work.length() / 8)];
			String temp = "";
			int index = 0;
			for (int y = 0; y < ret.length; y++) {
				for (int x = 0; x < sectionSize; x++) {
					temp += Work.charAt(index++);
				}
				ret[y] = temp;
				temp = "";
			}
			return ret;
		}

		private static String autoFill(int x, String a) {// x = bools to
			// add
			String ret = a;
			int z = 0;
			for (int y = 0; y < x; y++) {
				if (y > a.length())
					ret += '0';
			}

			return ret;
		}

		public Raster put(byte[] value) {
			System.out.println("[FONTING][RASTER][INPUT] : "
					+ ByteConventions.toBinaryString(value));
			bb.put(value);
			return this;
		}
	}

	public static class Syms {
		ArrayList<Raster> CharData = new ArrayList<Raster>();

		int charIndex = 0;

		private final int width, height;

		private Syms(int w, int h) {
			width = w;
			height = h;
		}

		public byte[] getCharData(int index) {
			return CharData.get(index).getData();
		}

		public Rectangle getDimentions() {
			return new Rectangle(width, height);
		}

		private void registerChar(Raster a) {
			CharData.add(a);
			charIndex++;
		}

		public int getCharIndex(){
			return charIndex;
		}
		
		public void addChar(String[] a) {
			Raster temp = new Raster(width, height);
			for (String s : a) {
				temp.put(s);
			}
			registerChar(temp);
		}

		public void save(String name) {
			System.out.println("[FONTING][SYMS][SAVING] : START");
			CustomFiles temp = CustomFileCreator.CreateNewFile(name + ".syms",
					new byte[] { 0x00, (byte) CharData.size(), 0x00,
							(byte) width, 0x00, (byte) height });
			for (Raster a : CharData) {
				byte[] WriteData = a.getData();
				temp.WriteToFile(WriteData);
			}
			System.out.println("[FONTING][SYMS][SAVING] : FINISH");
		}

	}

	public Fonting() {

	}

	public static Syms LoadFont(String FileName) {
		try {
			System.out.println("[FONTING][LOADFONT][LOADING] : " + FileName
					+ ".syms");
			SymsFile SS = new SymsFile(FileName+".syms");
			byteSegement[] CharData = SS.getCharData();

			Syms ret = new Syms(SS.getCharDimentions().width,
					SS.getCharDimentions().height);
			for (byteSegement a : CharData) {
				ret.registerChar(new Raster(SS.getCharDimentions().width, SS
						.getCharDimentions().height).put(a.getValue()));
			}
			System.out.println("[FONTING][LOADFONT][LOADED] : " + FileName
					+ ".syms");
			return ret;
		} catch (Exception e) {
			System.out.println("[FONTING][LOADFONT][LOADING_FAILURE] : "
					+ FileName);
			e.printStackTrace();
		}

		return null;
	}

	public static String[] getCreatedFonts(String dir) {
		File Dir = new File(dir);
		Strings.LINES Ret = new LINES();

		if (Dir.exists()) {
			if (Dir.isDirectory()) {
				for (String a : Dir.list()) {
					if (a.endsWith(".syms"))
						Ret.add(a);
				}
				return Ret.getAllLines();
			} else
				System.err.println("[FONTING][CHECKFONTS][ERR] : \"" + dir
						+ "\" IS NOT A DIRECTORY");
		} else
			System.err.println("[FONTING][CHECKFONTS][ERR] : THE DIRECTORY \""
					+ dir + "\" DOES NOT EXIST");

		return null;
	}

	public static Syms createNewFont(int width, int height, String[][] data) {
		if (width % 8 == 0 && height / 8 >= 1) {
			Syms ret = new Syms(width, height);
			for (String[] Char : data) {
				ret.addChar(Char);
			}
			return ret;
		}
		System.err.println("Both width and height must be devidable by 8.");
		return null;
	}

	private static String boolArrtoBin(boolean... a) {
		String ret = "";
		for (boolean s : a) {
			if (s) {
				ret += "1";

			} else
				ret += "0";
		}
		return ret;
	}

	private static byte binStringToByte(String a) {
		System.out.println("[FONTING][BINSTR>BYTE][INPUT] : " + a);
		int s = 0;
		int x = a.length() - 1;
		for (char z : a.toCharArray()) {
			if (z == '1')
				s += Math.pow(2, x);
			x--;
		}
		System.out.println("[FONTING][BINSTR>BYTE][RESULT] : " + s);
		return (byte) s;
	}
}
