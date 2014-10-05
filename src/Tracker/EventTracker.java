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

	private EventTracker(File e) {
		System.out.println("Starting event tracker---" + e.getName());
		Output_File = e;
		try {
			e.createNewFile();
			Output = new PrintWriter(Output_File);
			stampLenth = 26;
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}

	}

	public static EventTracker init(File output) {
		return new EventTracker(output);
	}

	public static EventTracker init(String output) {
		return new EventTracker(new File(output));
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
			ToPrint[x] = arr[x].toString();
		}
		String out = "";
		for (String a : ToPrint) {
			out += a;
			out += Formating.Strings.space("\n", stampLenth, ' ');
		}
		Write(out, 0);
	}

	public void Write(String S, int degree) {
		String OutputTime = new Time(new Date().getTime()).toString();
		String Outputs;
		Outputs = "[" + OutputTime + "]" + Types[degree] + " : ";
		Outputs += Formating.Strings.space("", stampLenth - Outputs.length(),
				' ') + S;

		Output.println(Outputs);
		Output.flush();
	}

}
