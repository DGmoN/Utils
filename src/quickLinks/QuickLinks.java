package quickLinks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import Formating.Misc;

/* This is a abstract class met for quick method links to buttons and so.
 * The Method object is registered and then recalled to the object
 * */

public abstract class QuickLinks {

	/** START REGISTERY **/
	private static QuickLinks QUICKENDS;

	public static boolean init() {
		QUICKENDS = new QuickEnds();
		return true;
	}

	private static final ArrayList<QuickLinks> RegisterdLinkCashes = new ArrayList<QuickLinks>();

	static boolean registerCash(QuickLinks a) {
		System.out.println("Registering: " + a.NAME);
		if (RegisterdLinkCashes.contains(a))
			return false;
		else
			RegisterdLinkCashes.add(a);
		System.out.println("Registed");
		return true;
	}

	public static void invoke(String trgt, Object... args) {
		String CashName = trgt.substring(0, trgt.indexOf(":")), targerMethod = trgt
				.substring(trgt.indexOf(":") + 1, trgt.length());

		System.out.println("Invoking: " + targerMethod + " - from - "
				+ CashName);

		for (QuickLinks a : RegisterdLinkCashes) {
			if (a.NAME.equals(CashName)) {
				if (a.contains(targerMethod)) {
					a.invokeMethod(targerMethod, args);
					return;
				}
			}
		}
		System.err.println("The method is not hear");
	}

	/** END REGISTRY **/

	public final String NAME;

	public ArrayList<Method> Methods = new ArrayList<Method>(); // Class Methods

	public QuickLinks(String Name) {
		NAME = Name;
		for (Method a : this.getClass().getMethods()) {
			if (!Misc.Contains(a, QuickLinks.class.getMethods())) {
				System.out.println("Adding method -> " + NAME + ":"
						+ a.getName());
				Methods.add(a);
			}
		}
		registerCash(this);
	}

	public boolean contains(String methodName) {
		for (Method a : Methods) {
			if (a.getName().equals(methodName))
				return true;
		}
		return false;
	}

	private void invokeMethod(String name, Object... args) {
		for (Method a : Methods) {
			if (a.getName().equals(name))
				try {
					a.invoke(this, args);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
		}
	}
}
