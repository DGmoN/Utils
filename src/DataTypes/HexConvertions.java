package DataTypes;

public class HexConvertions {

	static final char[] Hexvals = new char[] { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static int getIndexforHex(char s) {
		int x = 0;
		for (char z : Hexvals) {
			if (z == s) {
				return x;
			}
			x++;
		}
		return 0;
	}

	public static int hexToInt(String hex) {
		int ret = 0;
		String trueHex = hex.replace("0x", "");

		for (int x = 0; x < trueHex.length(); x++) {
			ret += getIndexforHex(trueHex.toCharArray()[x])
					* Math.pow(16, trueHex.length() - x - 1);
		}

		return ret;
	}
}
