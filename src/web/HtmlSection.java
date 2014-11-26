package web;

import java.io.File;
import java.util.Scanner;

import Formating.Strings;
import Formating.Strings.LINES;

public class HtmlSection {

	private static final String[] tags_nClosing = new String[] { "a", "abbr",
			"acronym", "address", "applet", "article", "aside", "audio", "b",
			"basefont", "dbi", "dbo", "big", "blockquote", "body", "button",
			"canvas", "caption", "center", "cite", "code", "colGroup",
			"datalist", "dd", "del", "details", "dfn", "dialog", "dir", "div",
			"dl", "dt", "em", "fieldset", "figcaption", "figure", "font",
			"footer", "form", "frame", "frameset", "h1", "h2", "h3", "h4",
			"h5", "h6", "head", "header", "hgroup", "html", "i", "iframe",
			"ins", "kbd", "keygen", "lable", "legend", "li", "main", "map",
			"mark", "menu", "menuitem", "meter", "nav", "noframes", "noscript",
			"object", "ol", "optgroup", "option", "output", "p", "pre",
			"progress", "q", "rp", "rt", "ruby", "s", "samp", "script",
			"section", "select", "small", "source", "span", "strike", "strong",
			"style", "sup", "summary", "table", "tbody", "textarea", "tfoot",
			"th", "thread", "time", "title", "tr", "track", "tt", "u", "ul",
			"var", "video", "wbr" };
	private static final String[] tags_Closing = new String[] { "br", "hr",
			"meta", "link", "base", "img", "embed", "param", "area", "col",
			"input" };

	Strings.LINES Lines;

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

	public String Tag;

	SectionStack Subsections = new SectionStack();

	public HtmlSection(String tag) {
		Tag = tag;
		Lines = new LINES();
		Comments = new LINES();
	}

	private HtmlSection() {
		Tag = "Main";
		Lines = new LINES();
		Comments = new LINES();
	}

	public void lasso() {
		boolean open = false;
		HtmlSection Subsection = null;
		String temp, tag;
		Scanner scan;
		boolean removeLn = false;
		while ((temp = Lines.nextLine(false)) != "EOL") {
			scan = new Scanner(temp);
			try {
				temp = scan.next();
			} catch (Exception e) {

			}

			if (open) {
				Subsection.Lines.add(temp);
			}

			if (!temp.startsWith("<!")) {
				tag = getTag(temp);
				if (temp.startsWith("</")) {
					if (open) {
						if (tag.equals(Subsection.Tag)) {
							open = false;
							System.out.println("Closing : " + Tag + " : "
									+ Subsection.Tag);
							Subsection.lasso();
							Subsections.add(Subsection);
						}
					}
				} else {
					if (temp.contains("<") && !temp.contains("/")) {
						if (!open) {
							open = true;
							Subsection = new HtmlSection(tag);
							System.out.println("Starting new section : " + Tag
									+ " : " + Subsection.Tag);
						}
					}
				}
			} else {
				Comments.add(temp);
			}

			Lines.remove(temp);
		}
	}

	public int getSectionCount() {
		return Subsections.size;
	}

	public String getTag(String s) {
		String ret = "";
		boolean open = false;
		for (char d : s.toCharArray()) {
			if (d == '<') {
				open = true;
			} else if (d == '>' || d == ' ') {
				break;
			}
			if (open && !(d == '>' || d == ' ' || d == '<' || d == '/'))
				ret += d;
		}
		return ret;
	}

	public void printTree(int x) {
		int a = x + 1;
		for (HtmlSection ss : Subsections.getSections()) {
			System.out.println(Strings.genSeparator(x, ' ') + ss.Tag);
			ss.printTree(a);
		}
	}

	public void fin() {
		System.out.println("Finilizing :: " + Tag);
		int x;
		for (x = 0; x < tags_Closing.length; x++) {
			if (tags_nClosing[x].equals(Tag)) {
				break;
			}
		}
		switch (x) {
		case 0: // Defines a hyperlink
			System.out.println(Tag + " : is a hyperlink");
			break;
		case 1: // Defines an abbreviation
			System.out.println(Tag + " : is an abreviation");
			break;
		case 2: // Not supported in HTML5.
				// Defines an acronym
			System.out.println(Tag + " : is an acronym");
			break;	
		case 3: // Defines contact information for the author/owner of a document
			System.out.println(Tag + " : is an address");
			break;
		case 4: // Not supported in HTML5.
				// Defines an embedded applet
			System.out.println(Tag + " : is an applent decleration");
			break;
		case 5: // Defines an area inside an image-map
			System.out.println(Tag + " : is an image-map area decleration");
			break;
		}
		

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
