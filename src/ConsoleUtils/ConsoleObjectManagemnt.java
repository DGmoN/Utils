package ConsoleUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ConsoleObjectManagemnt {

	private static ConsoleObjectManagemnt INSTANCE;
	private ConsoleInterface CI;

	public ArrayList<ConsoleObject> Objects = new ArrayList<ConsoleObject>();

	private ConsoleObjectManagemnt(ConsoleInterface a) {
		CI = a;
	}

	public void registerObject(ConsoleObject a) {
		if (!Objects.contains(a)) {
			Objects.add(a);
			for (Method s : a.Methods) {
				CI.registerCommand(a.SRC, s.getName(), "Object." + a.getName()
						+ "." + s.getName());
			}
		}
	}

	public void listObjectCommands(String name) {
		for (ConsoleObject a : Objects) {
			if (a.getName().equals(name)) {
				System.out.println("Object found:");
				for (Method s : a.Methods)
					System.out.println("-*- " + s.getName());
				return;
			}
		}
		System.out.println("!!!No sutch Object!!!");
	}

	public void removeObject(String name) {
		for (ConsoleObject a : Objects) {
			if (a.Name.equals(name))
				Objects.remove(a);
		}
	}

	public static void init(ConsoleInterface a) {
		INSTANCE = new ConsoleObjectManagemnt(a);
		a.COM = INSTANCE;
		INSTANCE.CI.registerCommand(INSTANCE, "listObjectCommands", "list",
				"Object");
		INSTANCE.CI.registerCommand(INSTANCE, "removeObject", "removeObject",
				"Object");

	}

}
