package web;

import web.HtmlUtils.HTMLGrouping;
import Files.TAGSFile;

public abstract class TAGHelper {

	private static boolean init = false;
	private static HTMLGrouping[] tags = null;

	static class HTMLTag {
		final HTMLGrouping _Tag;
		final boolean selfClosing;

		final HTMLGrouping[] Attrebutes;

		public HTMLTag(HTMLGrouping tag) {
			_Tag = tag;
			Attrebutes = new HTMLGrouping[tag.attbr.getSize()];
			selfClosing = tag.isSelfClosing();
			int x = 0;
			for (Object s : tag.attbr.clear()) {
				Attrebutes[x] = (HTMLGrouping) s;
				x++;
			}
		}

		public String getDescription() {
			return _Tag.getDescription();
		}

		public String getTag() {
			return _Tag.getName();
		}

		public void interprit(String openingTag) {

		}
	}

	public static HTMLTag getTag(String ttg) {
		if (!init) {
			init = true;
			int x = 0;
			try {
				TAGSFile ss = new TAGSFile("TAGS.tags");
				tags = new HTMLGrouping[ss.tag];
				for (Object s : ss.tags.clear()) {
					tags[x] = (HTMLGrouping) s;
					x++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (HTMLGrouping s : tags) {
			if (ttg.startsWith("<" + s.getName())
					|| ttg.startsWith("</" + s.getName())) {
				return new HTMLTag(s);
			}
		}
		return null;
	}
}
