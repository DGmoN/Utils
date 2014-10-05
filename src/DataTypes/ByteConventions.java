package DataTypes;

import Formating.Strings;

public class ByteConventions {

	static final char[] Hexvals = new char[] { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static class byteSegement {
		private byte[] Range;

		private final int ofset, size;

		private int space = 0;

		byte[] Value;

		public byteSegement(int ofset, int size, byte... range) {
			this.ofset = ofset;
			this.size = size;
			this.Range = range;
			updateValue();
		}

		public byteSegement(int ofset, int size, byteSegement range) {
			this.ofset = ofset;
			this.size = size;
			this.Range = range.getValue();
			updateValue();
		}

		public void invert() {
			byte[] ret = new byte[Value.length];
			int a = 0;
			for (byte x : Value) {
				ret[a++] = (byte) (255 - x);
			}
			Value = ret;
		}

		public byteSegement getNext(int x) {
			System.out
					.println("[BYTECONVENTIONS][BYTESEGMENT][GETNEXT][INPUT] : "
							+ x);
			byte[] ret = new byte[x];
			for (int y = 0; y < x; y++) {
				ret[y] = Range[y + space];
			}
			space += x;
			System.out
					.println("[BYTECONVENTIONS][BYTESEGMENT][GETNEXT][OUTPUT_LEN] : "
							+ ret.length);
			return new byteSegement(0, x, ret);
		}

		public void flip() {
			byte[] ret = new byte[Value.length];
			int x = Value.length - 1;
			for (byte s : Value) {
				ret[x--] = s;
			}
			Value = ret;
		}

		public String toHex() {
			StringBuilder sb = new StringBuilder();

			for (byte s : Value)
				sb.append(String.format("%02X", s));
			return sb.toString();
		}

		public int toInt() {
			flip();
			int ret = 0;

			int x = 0;

			for (byte e : Value) {
				if (e < 0) {
					ret += (256 + e) * Math.pow(16, x);
				} else {
					ret += e * Math.pow(16, x);
				}
				x += 2;
			}

			flip();
			return ret;
		}

		private void updateValue() {
			Value = new byte[size];
			for (int x = 0, y = 0; x < Range.length; x++) {
				if (x >= ofset && x < ofset + size) {
					Value[y++] = Range[x];
				}
			}
		}

		public byte[] getValue() {
			return Value;
		}
	}

	public static String toBinaryString(byte... bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
		for (int i = 0; i < Byte.SIZE * bytes.length; i++)
			sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0'
					: '1');
		return sb.toString();
	}

	public static double compare(byte[] a, byte[] b) {
		int x = 0, mathch = 0;
		for (byte s : a) {
			for (byte d : b) {
				if (d == s) {
					mathch++;
					break;
				}
			}
		}
		return mathch / b.length;
	}

	public static boolean compare2bool(byte[] a, byte[] b) {
		if (compare(a, b) < 1)
			return false;
		else
			return true;
	}

	public static String byteToHex(byte x) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%02X", x));
		return sb.toString();
	}

	public static String bytesToHexes(byte... ar) {
		String out = "";
		for (byte a : ar) {
			out += byteToHex(a);
		}
		return out;
	}

	public static long TValOf(byte... a) {
		long ret = 0;
		char[] bits = new char[a.length * 2];
		for (int x = 0; x < a.length; x++) {
			if (a.length - x - 1 == 0) {
				if (a[x] < 0) {
					ret += (254 - a[x]);
				} else {
					ret += (a[x]);
				}
			} else {
				if (a[x] < 0) {
					ret += (254 - a[x]) * Math.pow(16, (a.length - x - 1) * 2);
				} else {
					ret += (a[x]) * Math.pow(16, (a.length - x - 1) * 2);
				}
			}
		}

		return ret;
	}

	public static byte[] byteStringtoByteArr(String a) {

		byte[] ret = new byte[(a.length() / 2) + 1];
		String temp = "";
		int val = 0;
		int x = 0;
		for (x = 0; x < a.length(); x++) {
			temp += a.charAt(x);
			if (temp.length() == 2) {
				val = (getValFor(temp.charAt(0)) * 16)
						+ (getValFor(temp.charAt(1)));
				ret[x / 2] = (byte) val;
				temp = "";
			}
		}
		if (temp.length() > 0)
			val = (getValFor(temp.charAt(0)) * 16)
					+ (getValFor(temp.charAt(1)));
		ret[x / 2] = (byte) val;
		return ret;
	}

	public static String byteSequenceToStrings(byte... a) {
		String d = "";
		for (byte s : a) {
			d += (char) s;
		}
		return d;
	}

	private static int getValFor(char a) {
		int x = 0;
		for (char s : Hexvals) {
			if (s == a) {

				return x;
			}
			x++;
		}
		return 0;

	}

	public static String flipByte(String s) {
		String ret = "";

		int x;
		for (x = s.length() - 1; x > 0; x--) {
			ret += s.toCharArray()[x];
		}
		ret += s.toCharArray()[x];

		return ret;
	}

	public static int bytesToInt(byte... bytes) {
		String[] BYTES = new String[bytes.length];
		for (int x = 0; x < bytes.length; x++) {
			BYTES[x] = Strings.remove(byteToHex(bytes[x]), ' ');
		}
		String bigWord = Strings.combine("", 0, BYTES);

		return HexConvertions.hexToInt(flipByte(bigWord));

	}
}
