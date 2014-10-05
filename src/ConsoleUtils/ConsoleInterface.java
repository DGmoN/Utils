package ConsoleUtils;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInterface {

	private class Command {
		final Method target;
		final String command;
		final Object src;

		boolean isObject = false;

		public Command(Object src, Method a, String Command, String groupName) {
			target = a;
			this.src = src;
			if (groupName != null) {
				command = groupName + "." + Command;
			} else {
				command = Command;
			}
		}

		public String getCommand() {
			return command;
		}

		public String toString() {
			return "CMD:" + command;
		}

		public void invoke(Object... args) throws Exception,
				IllegalArgumentException, InvocationTargetException {

			if (!target.getReturnType().toString().equals("void")) {
				System.out.println(target.invoke(src, args));
			} else {
				target.invoke(src, args);
			}
		}
	}

	private Thread lineReader = new Thread() {
		Scanner scan, scan2;

		@Override
		public void run() {

			// init phase
			scan = new Scanner(IS);
			String temp = null;

			while (true) {
				scan2 = new Scanner(temp = scan.nextLine());
				try {
					TRY(scan2.next(), getArgs(temp));
				} catch (Exception e) {
					e.printStackTrace();
				}
				temp = null;
			}
		}

		private Object[] getArgs(String a) {
			ArrayList<String> toReturn = new ArrayList<String>();
			boolean start = false;
			String temp = "";
			for (char s : a.toCharArray()) {
				if (start) {
					if (s != ' ') {
						temp += s;
					} else {
						toReturn.add(temp);
						temp = "";
					}
				} else if (s == ' ') {
					start = true;
				}
			}
			toReturn.add(temp);

			if (!start)
				return null;

			return toReturn.toArray();
		}
	};

	private ArrayList<Command> Commands = new ArrayList<Command>();

	final InputStream IS;

	public ConsoleObjectManagemnt COM;

	public ConsoleInterface(InputStream IS) {
		this.IS = IS;
		lineReader.setDaemon(true);
		lineReader.start();
		registerCommand(this, "listCommands", "list");
		registerCommand(this, "closeProgram", "Exit");
	}

	public void registerCommand(Object src, String methodName, String cmd) {
		Method[] temp = src.getClass().getMethods();
		for (Method a : temp) {
			if (a.getName().equals(methodName)) {
				Command s = new Command(src, a, cmd, null);
				s.isObject = true;
				Commands.add(s);
				return;
			}
		}
		System.out.println("MethodNotFound!!!");
	}

	/*
	 * src = Source object, d[0] = method Name, d[1] = command, d[2] = group
	 */
	public void registerCommand(Object src, String... d) {
		Method[] temp = src.getClass().getMethods();
		for (Method a : temp) {
			if (a.getName().equals(d[0])) {
				Commands.add(new Command(src, a, d[1], d[2]));
				return;
			}
		}
		System.out.println("MethodNotFound!!!");
	}

	private void TRY(String cmd, Object... tt) throws Exception {
		for (Command a : Commands) {
			if (a.getCommand().equals(cmd)) {
				a.invoke(tt);
				return;
			}
		}
		System.err.println("Command: " + cmd + " not found");
	}

	public void closeProgram() {
		System.exit(0);
	}

	public void listCommands(String Object) {
		System.out.println("Listing Commands: ");
		for (Command a : Commands) {
			if (a.command.startsWith(Object))
				System.out.println("---" + a.command);
			else if (Object.equals("main")) {
				if (!a.isObject)
					System.out.println("---" + a.command);
			}
		}
		System.out.println("Listing Objects: ");
		for (ConsoleObject a : COM.Objects) {
			System.out.println("---" + a.getName());
		}
	}
}
