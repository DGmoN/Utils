package DataTypes;

public class ArrayConventions {

	public static class genericStack<type> {

		private Object[] Data = new Object[0];
		int size = 0;

		public genericStack(type... e) {
			if (e.length > Data.length) {
				Data = e;
				size = e.length - 1;

			}
		}

		public Object[] getAll() {
			return Data;
		}

		public void add(Object[] line) {
			for (Object s : line) {
				add((type) s);
			}
		}

		public void add(type e) { // add object to top of the stack, stack
									// limit
									// undefiend
			Object[] NewData = new Object[++size];
			for (int x = 0; x < size - 1; x++) {
				NewData[x] = Data[x];
			}
			NewData[size - 1] = e;
			Data = NewData;

		}

		// abcdefghijklmnopqrstuvwxyz
		public type get() {
			type ret;
			Object[] NewData = new Object[--size];
			for (int x = 0; x < size; x++) {
				NewData[x] = Data[x];
			}
			ret = (type) Data[size];
			Data = NewData;

			return ret;
		}

		public Object[] clear() {
			int startSize = size;
			Object[] ret = new Object[size];
			for (int x = 0; x < startSize; x++) {
				ret[x] = get();
			}
			size = 0;
			Data = new Object[0];
			return ret;
		}

		public int getSize() {
			return size;
		}

		public boolean empty() {
			return size == 0;
		}

	}

}
