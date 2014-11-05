package Tracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.Format;
import java.util.Date;

public class EventTracker {

	final File Output_File;

	PrintWriter Output;

	int stampLenth;

	InputStream in;
	BufferedReader ISR;

	static final String[] Types = new String[] { "[Info]", "[Warning]",
			"[Error]" };

	Thread ActiveToReader;

	final Class SRC;

	private EventTracker(File e, Class src) {
		long now = System.currentTimeMillis();
		System.out.println("Starting event tracker---" + e.getName() + "---"
				+ src.getCanonicalName());
		SRC = src;
		Output_File = e;
		try {
			if (!new File(Output_File.getParentFile().getPath()).exists()) {
				new File(Output_File.getParentFile().getPath()).mkdir();
			}
			e.createNewFile();
			Output = new PrintWriter(Output_File);
			stampLenth = 26;
			System.out.println("Done with no probelms");
			Write("Time taken to init: " + (System.currentTimeMillis() - now)
					+ "ms", 0);
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}

	}

	public static EventTracker init(File output, Class src) {
		return new EventTracker(output, src);
	}

	public static EventTracker init(String output, Class src) {
		return new EventTracker(new File(output), src);
	}

	public void Write(Exception e) {
		e.printStackTrace(Output);
	}

	public void newParagraph() {
		Write("----------------------------------", 0);
	}

	public void Write(Object... arr) {
		String[] ToPrint = new String[arr.length];
		for (int x = 0; x < arr.length; x++) {
			if (arr[x] != null)
				ToPrint[x] = arr[x].toString();
		}
		String out = "";
		for (String a : ToPrint) {
			out += a;
			out += Formating.Strings.space("\n", stampLenth, "");
		}
		Write(out, 0);
	}

	public void Write(String S, int degree) {
		String OutputTime = new Time(new Date().getTime()).toString();
		String Outputs;
		Outputs = "[" + OutputTime + "]" + "[" + SRC.getCanonicalName() + "]"
				+ Types[degree] + " : ";
		Outputs += Formating.Strings.space("", stampLenth - Outputs.length(),
				" ") + S;

		Output.println(Outputs);
		Output.flush();
	}

}
