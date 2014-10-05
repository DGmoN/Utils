package Formating;

public class Misc {

	public static class segment<Val> {

		private Val[] Range;

		private final int ofset, size;

		Val[] Value;

		public segment(int ofset, int size, Val... range) {
			this.ofset = ofset;
			this.size = size;
			this.Range = range;
			updateValue();
		}

		private void updateValue() {
			Value = (Val[]) new Object[size];
			for (int x = 0, y = 0; x < Range.length; x++) {
				if (x > ofset && x < ofset + size) {
					Value[y++] = Range[x];
				}
			}
		}

		public Val[] getValue() {
			return Value;
		}

	}

	public Misc() {

	}

	public static byte[] extractBytes(int startIndex, int size, byte[] items) {
		int newSlot = 0;
		byte[] ret = new byte[size];

		for (int x = 0; x < startIndex+size; x++) {
			if (x >= startIndex)
				ret[newSlot++] = items[x];
		}

		return ret;
	}

	public static Object[] extract(int startIndex, int EndIndex,
			Object... items) {
		int newSlot = 0;
		Object[] ret = new Object[EndIndex - (startIndex) - 1];
		for (int x = 0; x < EndIndex; x++) {
			if (x > startIndex)
				ret[newSlot++] = items[x];
		}

		return ret;
	}

	public static boolean Contains(Object a, Object... b) {
		for (Object s : b) {
			if (s.equals(a))
				return true;
		}
		return false;
	}

	public static int getIndex(Object a, Object... b) {
		int x = 0;
		for (Object s : b) {
			if (s.equals(a))
				return x;
			x++;
		}
		return 0;
	}

}
