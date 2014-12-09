package web;

import java.io.File;

import web.TAGHelper.HTMLTag;
import Formating.Strings;
import Formating.Strings.LINES;

public class HtmlSection {

	Strings.LINES Lines;
	Strings.LINES DATA = new LINES();
	Strings.LINES Comments;

	private class SectionStack {
		private HtmlSection[] Data = new HtmlSection[0];
		int size = 0;

		public void add(HtmlSection e) { // add object to top of the stack,
											// stack limit undefiend
			if (e == null)
				return;
			HtmlSection[] NewData = new HtmlSection[++size];
			for (int x = 0; x < size - 1; x++) {
				NewData[x] = Data[x];
			}
			NewData[size - 1] = e;
			Data = NewData;
		}

		public HtmlSection[] getSections() { // does not change the array
			return Data;
		}

		public HtmlSection get() {
			HtmlSection ret;
			HtmlSection[] NewData = new HtmlSection[--size];
			for (int x = 0; x < size; x++) {
				NewData[x] = Data[x];
			}
			ret = Data[size];
			Data = NewData;
			return ret;
		}

	}

	public String TagLine;
	HTMLTag tag;

	SectionStack Subsections = new SectionStack();

	public HtmlSection(String tag) {
		TagLine = tag;
		Lines = new LINES();
		Comments = new LINES();
	}

	private HtmlSection() {
		TagLine = "<Main>";
		Lines = new LINES();
		Comments = new LINES();
	}

	public void lasso() {
		boolean open = false, read = false;
		HtmlSection Subsection = null;
		String temp;
		int layer = 0;
		System.out.println("Reading layer : " + TagLine);
		while ((temp = Lines.nextLine(false)) != "EOL") {
			int x = 0;
			tag = getTag(temp);
			if (tag != null) {
				if (!temp.contains("!")) {
					if (open) {
						Subsection.Lines.add(temp);
					}
					if (!temp.contains("/")) {

						if (!temp.contains("<"))
							DATA.add(temp);

						if (Subsection == null) {
							open = true;
							System.out.println("OOPENIG : " + tag.getTag());
							Subsection = new HtmlSection(temp);
						}
						if (!tag.selfClosing)
							layer++;
						else {
							if (Subsection != null) {
								open = false;
								System.out
										.println("CLOOSING : " + tag.getTag());
								Subsections.add(Subsection);
								Subsection = null;
							}
						}
					} else {
						layer--;
						if (layer == 0) {
							if (Subsection != null) {
								open = false;
								System.out
										.println("CLOOSING : " + tag.getTag());
								Subsections.add(Subsection);
								Subsection = null;
							}
						}
					}

				}
				Lines.remove(temp);
			} else {
				if (Subsection != null) {
					Subsection.DATA.add(temp);
				}
				if (temp.startsWith("<!"))
					Comments.add(temp);
				else {
					DATA.add(temp);
					System.err.println("Line: " + temp + " has no tag");
				}
			}
		}
		for (HtmlSection s : Subsections.Data) {
			s.lasso();
		}
	}

	public String[] getLines() {
		return DATA.getAllLines();
	}

	public int getSectionCount() {
		return Subsections.size;
	}

	public HTMLTag getTag(String s) {
		boolean reading = false;
		String ret = "";
		for (char d : s.toCharArray()) {

			if (d == '<') {
				reading = true;
			}

			if (reading) {
				if ((d != '<' || d != '>'))
					ret += d;
			}

			if (d == '>') {
				reading = false;
				return TAGHelper.getTag(ret);
			}

		}
		return null;
	}

	public void printTree(int x) {
		int a = x + 1;
		for (HtmlSection ss : Subsections.getSections()) {
			System.out.println(Strings.genSeparator(x, ' ') + ss.TagLine);
			for (String s : ss.getLines()) {
				System.out.println(Strings.genSeparator(x, ' ') + s);
			}

			System.out.println(Strings.genSeparator(x, ' ') + ss.TagLine);
		}
	}

	public void fin() {
		System.out.println("Finilizing :: " + TagLine + "\t");
		boolean found = false;

		for (HtmlSection s : Subsections.getSections()) {
			s.fin();
		}
	}

	public static HtmlSection generateFromFile(File src) {
		HtmlSection ret = new HtmlSection();
		ret.Lines.loadFromFile(src);
		ret.lasso();
		ret.fin();
		return ret;
	}
}
