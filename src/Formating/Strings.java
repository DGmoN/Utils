package Formating;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Strings {

	private static final char[] ALFABETICAL = { 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z' }, NUMBERS = { '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9' };

	public static class LINES {
		private String[] lines;
		private int index = 0;

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

		public String nextLine() {
			if (index >= lines.length)
				return "EOL";
			if (index < lines.length) {
				if (lines[index].endsWith("\n"))
					return lines[index++];
				else {
					return lines[index++] + "\n";
				}
			} else
				return "EOL";
		}

		public String[] getAllLines() {
			String[] out = new String[lines.length];
			int x = 0;
			for (String a : lines) {
				if (a != null)
					out[x++] = a;

			}

			return out;
		}

	}

	public static boolean isNumerical(char a) {
		System.out.println(a);
		for (char s : NUMBERS) {
			if (a == s) {
				return true;
			}
		}
		return false;
	}

	// The size indicates the length of a Item in the array

	public static String format(int TSize, char sep, Object... strings) {
		String ret = "";
		String temp;
		for (Object s : strings) {
			temp = s.toString();
			ret += temp;
			ret = space(ret, TSize - temp.length(), sep + "");
		}
		return ret;
	}

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

	public static String space(String src, int spaceCount, String dd) {
		for (int x = 0; x < spaceCount; x++) {
			src += dd;
		}
		return src;
	}

	public static String insertEvery(int interval, String separation, String src) {
		String ret = "";
		for (int x = 0; x < src.length(); x++) {
			if (x!= src.length())
			if (x % interval == 0)
				ret += separation;

			ret += src.charAt(x);
		}
		return ret;
	}

	public static String genSeparator(int len, char txt) {
		String ret = "";
		for (int x = 0; x < len; x++) {
			ret += txt;
		}
		return ret;
	}

	public static String remove(String trgt, char... cs) {
		String ret = "";

		for (char s : trgt.toCharArray()) {
			for (char z : cs) {
				if (s != z)
					ret += s;
			}
		}

		return ret;
	}

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

	public static String[] objectArrToStrings(Object... a) {
		String[] out = new String[a.length];
		int x = 0;

		for (Object s : a) {
			out[x++] = s.toString();
		}
		return out;
	}

	public static String getTime(char separater) {
		long temp = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("[dd" + separater + "MMM"
				+ separater + "yyyy] [HH" + separater + "mm]");
		Date resultdate = new Date(temp);
		return "" + sdf.format(resultdate);
	}
}
