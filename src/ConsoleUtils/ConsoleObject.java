package ConsoleUtils;

import java.lang.reflect.Method;

public class ConsoleObject {

	String Name;

	Method[] Methods;

	Object SRC;

	public ConsoleObject(Object src, String prefferdName) {
		Methods = src.getClass().getMethods();
		Name = prefferdName;
		SRC = src;
	}

	public void setName(final String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}
}
