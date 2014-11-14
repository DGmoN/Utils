package Formating;

import javax.xml.bind.util.ValidationEventCollector;

public abstract class Misc {

	public static class segment<Val> {


		
		int index = 0;

		Val[] Value;

		public segment(Val[] range) {

			this.Value = range;
		}
		
		public void reset(){
			index = 0;
		}
		
		public Object[] getRemaining(){
			int SIZE = Value.length-index;
			int x = index;
			Object[] ret = new Object[SIZE];
			while((x-index)<SIZE){
				ret[x-index] = Value[x];
				x++;
			}
			return ret;
		}
		
		public int getLenth(){
			return Value.length;
		}
		
		public Val getNext(){
			return Value[index++];
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
