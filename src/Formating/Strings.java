package Formating;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import DataTypes.ByteConventions;

public class Strings {

	private class StringStack {
		private String[] Data = new String[0];
		int size = 0;

		public StringStack(String... e) {
			if (e.length > Data.length) {
				Data = e;
				size = e.length - 1;

			}
		}

		public void add(String[] line) {
			for (String s : line) {
				add(s);
			}
		}

		public void add(String e) { // add object to top of the stack, stack
									// limit
									// undefiend
			String[] NewData = new String[++size];
			for (int x = 0; x < size - 1; x++) {
				NewData[x] = Data[x];
			}
			NewData[size - 1] = e;
			Data = NewData;

		}

		// abcdefghijklmnopqrstuvwxyz
		public String get() {
			String ret;
			String[] NewData = new String[--size];
			for (int x = 0; x < size; x++) {
				NewData[x] = Data[x];
			}
			ret = Data[size];
			Data = NewData;

			return ret;
		}

		public String[] clear() {
			int startSize = size;
			String[] ret = new String[size];
			for (int x = 0; x < startSize; x++) {
				ret[x] = get();
			}
			size = 0;
			Data = new String[0];
			return ret;
		}

		public boolean empty() {
			return size == 0;
		}
	}

	private static final char[] ALFABETICAL = { 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z' }, NUMBERS = { '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9' };

	public static boolean isAlfabetical(char x) {
		for (char s : ALFABETICAL) {
			if (s == x)
				return true;
		}
		return false;
	}

	public static boolean isSymbol(char x) {
		return !(isAlfabetical(x) && isNumerical(x));
	}

	public static class LINES {
		private String[] lines;
		private int index = 0;

		public void loadFromFile(File ss) {
			try {
				BufferedReader s = new BufferedReader(new FileReader(ss));
				String read;
				while (true) {
					read = s.readLine();
					if (read == null)
						break;
					add(read);

				}
			} catch (Exception e) {
			}
		}

		public void replase(String s, String rep) {
			boolean rem = false;
			int remCount = 0;
			for (int x = 0; x < lines.length; x++) {
				if (lines[x] != null)
					if (lines[x].equals(s)) {
						lines[x] = rep;
						if (index > x) {
							index--;
						}
						break;
					}
			}

		}

		public void remove(String s) {
			boolean rem = false;
			int remCount = 0;
			for (int x = 0; x < lines.length; x++) {
				if (lines[x] != null)
					if (lines[x].equals(s)) {
						lines[x] = null;
						rem = true;
						if (index > x) {
							index--;
						}
						break;
					}
			}

			if (rem) {
				String[] neW = new String[lines.length - 1];
				int a = 0;
				for (String q : lines) {
					if (q != null) {
						neW[a++] = q;
					}
				}
				lines = neW;
			}
		}

		public String getAllAsSingle() {
			String ret = "";
			for (String l : lines) {
				ret += l + "\n";
			}
			return ret;
		}

		public LINES(String... Lines) {
			if (Lines != null) {
				this.lines = Lines;
				return;
			}
			this.lines = new String[0];
		}

		public void add(String... lines) {
			String[] newArr;
			if (this.lines != null)
				newArr = new String[this.lines.length + lines.length];
			else
				newArr = new String[lines.length];
			int x = 0;
			if (this.lines != null)
				for (String s : this.lines) {
					newArr[x++] = s;
				}
			for (String s : lines) {
				newArr[x++] = s;
			}

			this.lines = newArr;
		}

		public boolean contains(String a) {
			for (String s : lines) {
				if (s != null)
					if (s.equals(a))
						return true;
			}
			return false;
		}

		public int getSize() {
			if (lines == null)
				return 0;
			return lines.length;
		}

		public void reset() {
			index = 0;
		}

		public void clear() {
			index = 0;
			lines = null;
		}

		public String nextLine(boolean nextLn) {
			if (index >= lines.length)
				return "EOL";
			if (index < lines.length) {
				if (lines[index].endsWith("\n"))
					return lines[index++];
				else {
					if (nextLn)
						return lines[index++] + "\n";
					else
						return lines[index++];
				}
			} else
				return "EOL";
		}

		public String[] getAllLines() {
			return lines;
		}

	}

	public static boolean isNumerical(char a) {
		for (char s : NUMBERS) {
			if (a == s) {
				return true;
			}
		}
		return false;
	}

	// simply gets the amount of chars between 2 chars
	public static int charCountBetween(char a, char b, String src) {
		boolean counting = false;
		if (a == ' ')
			counting = true;

		int ret = 0;
		for (char s : src.toCharArray()) {
			if (s == b)
				break;
			if (counting)
				ret++;
			if (s == a)
				counting = true;
		}

		return ret;
	}

	// gets the substring between 2 chars
	public static String extractBetween(char a, char b, String src,
			int ignoreFirst) {
		String ret = "";
		boolean counting = false;
		if (a == ' ')
			counting = true;
		int count = 0;
		for (char s : src.toCharArray()) {
			if (s == b && counting)
				break;
			if (counting) {
				if (s != ' ')
					ret += s;

			}
			if (s == a) {
				if (count == ignoreFirst)
					counting = true;
				else
					count++;
			}
		}
		return ret;
	}

	// The size indicates the length of a Item in the array
	public static String format(int TSize, char sep, Object... strings) {
		String ret = "";
		String temp;
		for (Object s : strings) {
			temp = s.toString();
			ret += temp;
			ret = space(ret, TSize - temp.length(), sep + "", false);
		}
		return ret;
	}

	// combines strings with the separator
	public static String combine(String separator, int separationLength,
			String... strings) {
		String ret = "";
		if (strings != null)
			for (String s : strings) {
				if (s != null) {
					ret += s;
					if (!s.equals(strings[strings.length - 1]))
						for (int x = 0; x < separationLength; x++) {
							ret += separator;
						}
				}
			}
		return ret;
	}

	// inserts a space on iter the left or the right, depending on justify
	public static String space(String src, int spaceCount, String dd,
			boolean justify) {
		String ret = "";
		if (!justify)
			ret = src;
		for (int x = 0; x < spaceCount; x++) {
			ret += dd;
		}
		if (justify)
			ret += src;
		return ret;
	}

	// inserts a substring every interval
	public static String insertEvery(int interval, String separation, String src) {
		String ret = "";
		for (int x = 0; x < src.length(); x++) {
			if (x != src.length())
				if (x % interval == 0)
					ret += separation;

			ret += src.charAt(x);
		}
		return ret;
	}

	// creates a length of a specific char
	public static String genSeparator(int len, char txt) {
		String ret = "";
		for (int x = 0; x < len; x++) {
			ret += txt;
		}
		return ret;
	}

	// removes the char from the string
	public static String remove(String trgt, char... cs) {
		String ret = "";
		for (char s : trgt.toCharArray()) {
			if (!StringContains(new String(cs), s))
				ret += s;
		}

		return ret;
	}

	private static boolean StringContains(String s, char a) {
		for (char g : s.toCharArray()) {
			if (a == g) {
				return true;
			}
		}
		return false;

	}

	// returns the length of the longest string
	public static int getMaxLength(String... ar) {
		int x = 0;
		for (String a : ar) {
			if (a != null)
				if (a.length() > x) {
					x = a.length();
				}
		}
		return x;
	}

	// builds a string array of objects
	public static String[] objectArrToStrings(Object... a) {
		String[] out = new String[a.length];
		int x = 0;

		for (Object s : a) {
			out[x++] = s.toString();
		}
		return out;
	}

	// returns a time stamp
	public static String getTime(char separater) {
		long temp = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("[dd" + separater + "MMM"
				+ separater + "yyyy] [HH" + separater + "mm]");
		Date resultdate = new Date(temp);
		return "" + sdf.format(resultdate);
	}
}
