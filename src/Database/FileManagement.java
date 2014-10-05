package Database;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public abstract class FileManagement {

	private static ArrayList<Type> RegisterdTypes = new ArrayList<Type>();

	static class Type {

		final String[] Fields;
		String[] Values;

		String deseg;

		public Type(String Designation, String... data) {
			Fields = data;
			Values = new String[data.length];
			deseg = Designation;
		}

		public void setValues(String... s) {
			System.arraycopy(s, 0, Values, 0, s.length);
		}

		public String getValue(String field) {
			int x = 0;
			for (String z : Fields) {
				if (z.equals(field)) {
					if (x > Values.length) {
						System.err.println("The field: \"" + field
								+ "\" was not filled");
					}
					return Values[x];
				}
				x++;
			}
			return null;
		}

		public void setValue(String field, String assighnemnt) {
			int x = 0;
			for (String z : Fields) {
				if (z.equals(field)) {
					Values[x] = assighnemnt;
					return;
				}
				x++;
			}
		}

		public Type copy() {
			return new Type(deseg, Fields);
		}
	}

	static File configSrc;

	static char separator;

	public static void populateTypes(File configFile) {
		configSrc = configFile;
		configure();
	}

	public static Type getType(String Designation) {

		for (Type s : RegisterdTypes) {
			if (s.deseg.equals(Designation)) {
				try {

					return s.copy();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String[] format(String a) {
		int count = 0, index = 0;
		String[] Fields;
		for (char s : a.toCharArray()) {
			if (s == separator)
				count++;
		}
		Fields = new String[count + 1];
		String temp = "";
		for (char s : a.toCharArray()) {
			if (s != '~' && s != ':')
				temp += s;

			if (s == '~') {
				Fields[index] = temp;
				index++;
				temp = "";
			}
		}
		Fields[index] = temp;
		return Fields;
	}

	public static int countLines(File s) {
		int x = 0;
		try {
			BufferedReader re = new BufferedReader(new FileReader(s));
			while (true) {
				try {
					String t = re.readLine();
					if (t == null)
						throw new EOFException();
					x++;
				} catch (Exception e) {
					re.close();
					break;
				}
			}
		} catch (Exception e) {

		}
		return x;
	}

	private static void registerItem(String a) throws Exception {
		int count = 0, index = 0;
		String[] Fields;
		String desc = "";
		boolean hit = false;
		for (char s : a.toCharArray()) {
			if (s == separator)
				count++;
		}
		Fields = new String[count + 1];
		String temp = "";
		for (char s : a.toCharArray()) {
			if (s != '~' && s != ':')
				temp += s;
			if (hit) {
				if (s == '~') {
					Fields[index] = temp;
					index++;
					temp = "";
				}
			} else {
				if (s == ':') {
					hit = true;
					desc = temp;
					temp = "";
				}
			}
		}
		Fields[index] = temp;
		RegisterdTypes.add(new Type(desc, Fields));
		System.out.println("Registerd database type: " + desc);
	}

	private static void configure() {
		BufferedReader R;
		try {
			R = new BufferedReader(new FileReader(configSrc));
			String temp;
			while (true) {
				temp = R.readLine();
				if (temp.startsWith("#")) {
					if (temp.startsWith("#ValueSeparator")) {
						separator = temp.charAt(temp.length() - 1);
						System.out.println("Value separator set to: "
								+ separator);
					}
				} else {
					registerItem(temp);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + ": Error");
		}
	}
}
