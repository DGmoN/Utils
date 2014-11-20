package web;

import java.io.File;
import java.util.Scanner;

import Formating.Strings;
import Formating.Strings.LINES;

public class HtmlSection {

	private static final String[] tags_nClosing = new String[]{"a","abbr","acronym","address","applet","article"
		,"aside","audio","b","basefont","dbi", "dbo","big","blockquote","body","button","canvas","caption"
		,"center","cite","code","colGroup","datalist","dd","del","details","dfn","dialog","dir","div","dl","dt"
		,"em","fieldset","figcaption","figure","font","footer","form","frame","frameset","h1","h2","h3","h4",
		"h5","h6","head","header","hgroup","html","i","iframe","ins","kbd","keygen","lable","legend",
		"li","main","map","mark","menu","menuitem","meter","nav","noframes","noscript","object","ol"
		,"optgroup","option","output","p","pre","progress","q","rp","rt","ruby","s","samp","script","section",
		"select","small","source","span", "strike","strong","style","sup","summary","table","tbody","textarea","tfoot","th",
		"thread","time","title","tr","track","tt","u","ul","var","video","wbr"};
	private static final String[] tags_Closing = new String[]{"br","hr","meta","link","base","img","embed","param","area","col","input"};
	
	final String Type;

	Strings.LINES Lines;

	private class SectionStack {
		private HtmlSection[] Data = new HtmlSection[0];
		int size = 0;

		public void add(HtmlSection e) {  // add object to top of the stack, stack limit undefiend
			HtmlSection[] NewData = new HtmlSection[++size];
			for (int x = 0; x < size - 1; x++) {
				NewData[x] = Data[x];
			}
			NewData[size] = e;
			Data = NewData;
		}
		
		public HtmlSection get(){
			HtmlSection ret;
			HtmlSection[] NewData = new HtmlSection[--size];
			for (int x = 0; x < size; x++) {
				NewData[x] = Data[x];
			}
			ret = Data[size+1];
			Data = NewData;
			return ret;
		}
		
	}

	public String Tag;
	
	public HtmlSection(String type) {
		Type = type;
		Lines = new LINES();
	}

	public void lasso() {
		String temp;
		Scanner tt;
		boolean breaK = false;
		while(!(temp = Lines.nextLine()).equals("EOF")&&!breaK){
			tt = new Scanner(temp);
			System.out.println(tt.next());
		}
	}

	public static HtmlSection generateFromFile(File src) {
		HtmlSection ret = new HtmlSection("MainPage");
		ret.Lines.loadFromFile(src);
		ret.lasso();
		return ret;
	}
}
