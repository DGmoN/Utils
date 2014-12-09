package Files;

import java.io.BufferedReader;
import java.util.Scanner;

import web.HtmlUtils.HTMLGrouping;
import DataTypes.ArrayConventions.genericStack;

public class TAGSFile extends CustomFiles {

	private static final String[] defultTags = new String[] {
			"!alt~Specifies an alternative text for an image",
			"!disabled~Specifies that an input element should be disabled",
			"!href~Specifies the URL (web address) for a link",
			"!id~Specifies a unique id for an element",
			"!src~Specifies the URL (web address) for an image",
			"!style~Specifies an inline CSS style for an element",
			"!title~Specifies extra information about an element (displayed as a tool tip)",
			"!value~Specifies the value (text content) for an input element." };

	private static final String[] windowEvents = new String[] {
			"!onafterprint~Script to be run after the document is printed",
			"!onbeforeprint~Script to be run before the document is printed",
			"!onbeforeunload~Script to be run when the document is about to be unloaded",
			"!onerror~Script to be run when an error occur",
			"!onhashchange~Script to be run when there has been changes to the anchor part of the a URL",
			"!onload~Fires after the page is finished loading",
			"!onmessage~Script to be run when the message is triggered",
			"!onoffline~Script to be run when the browser starts to work offline",
			"!ononline~Script to be run when the browser starts to work online",
			"!onpagehide~Script to be run when a user navigates away from a page",
			"!onpageshow~Script to be run when a user navigates to a page",
			"!onpopstate~Script to be run when the window's history changes",
			"!onresize~Fires when the browser window is resized",
			"!onstorage~Script to be run when a Web Storage area is updated",
			"!onunload~Fires once a page has unloaded (or the browser window has been closed)" };

	private static final String[] formEvents = new String[] {
			"!onblur~Fires the moment that the element loses focus",
			"!onchange~Fires the moment when the value of the element is changed",
			"!oncontextmenu~Script to be run when a context menu is triggered",
			"!onfocus~Fires the moment when the element gets focus",
			"!oninput~Script to be run when an element gets user input",
			"!oninvalid~Script to be run when an element is invalid",
			"!onreset~Fires when the Reset button in a form is clicked",
			"!onsearch~Fires when the user writes something in a search field (for <input=\"search\">)",
			"!onselect~Fires after some text has been selected in an element",
			"!onsubmit~Fires when a form is submitted" };

	public genericStack<HTMLGrouping> tags = new genericStack<HTMLGrouping>();

	public int tag = 0;

	public TAGSFile(String trgt) throws Exception {
		super(trgt, 0, 0);
		read();
	}

	BufferedReader reaD;
	Scanner reader;

	public void read() {
		HTMLGrouping active = null, newAttr;
		String temp = new String(FileData);
		reader = new Scanner(temp);
		String nextVal;
		while (true) {
			try {
				nextVal = reader.nextLine();
				if (nextVal.startsWith("#")) {
					tags.add(active = new HTMLGrouping(nextVal));
					for (String g : defultTags) {
						active.addattbr(new HTMLGrouping(g));
					}
					for (String g : formEvents) {
						active.addattbr(new HTMLGrouping(g));
					}
					for (String g : windowEvents) {
						active.addattbr(new HTMLGrouping(g));
					}
					tag++;
				} else if (nextVal.startsWith("!")) {
					active.addattbr(newAttr = new HTMLGrouping(nextVal));
				}

			} catch (Exception e) {
				System.out.println("EOF:" + this);
				break;
			}

		}
		System.out.println("Tags read : " + tag);
	}

}
