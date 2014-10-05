package Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import Database.FileManagement.Type;

public class database {

	static File s = new File("Database"), configSrc;

	public static void init(String a) {
		try {
			if (a != null)
				configSrc = new File(a);
			else
				configSrc = new File("Database/config");
			if (!s.exists())
				s.mkdir();
			if (!configSrc.exists()) {
				configSrc.createNewFile();
				initConfigFile();
			}
			FileManagement.populateTypes(configSrc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initConfigFile() throws Exception {
		PrintWriter W = new PrintWriter(configSrc);
		W.println("#ValueSeparator=~");
		W.close();
	}

	private File src;
	private String Desegnatoin;
	private ArrayList<Type> TYPES = new ArrayList<Type>();

	public database(String fileName) {
		src = new File("Database/" + fileName);
		Desegnatoin = fileName;
		if (!src.isDirectory()) {
			for (Type z : readFile(Desegnatoin, src, Desegnatoin)) {
				TYPES.add(z);
			}
		} else {
			for (File s : src.listFiles()) {
				for (Type z : readFile(Desegnatoin + " : " + s.getName(), s,
						Desegnatoin)) {
					TYPES.add(z);
				}
			}
		}

		for (Type s : TYPES) {
			System.out.println(s.deseg);
			for (String d : s.Fields) {
				System.out.println("\t" + d + " : " + s.getValue(d));
			}
		}

	}

	private static Type[] readFile(String forcedDesect, File trgt,
			String srcDesect) {
		BufferedReader REad = null;

		try {
			REad = new BufferedReader(new FileReader(trgt));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int index = 0;
		Type[] typesToReturn = new Type[FileManagement.countLines(trgt)];
		while (true) {

			String temp = "";
			Type typeA;
			try {
				temp = REad.readLine();
				typeA = FileManagement.getType(srcDesect);
				if (typeA != null) {
					typeA.setValues(FileManagement.format(temp));
					typeA.deseg = forcedDesect;
					typesToReturn[index++] = typeA;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage() + ": Error");
				break;
			}
		}

		return typesToReturn;
	}
}
