package Formating;

public abstract class Formats {

	private static final char charPlaseHolder = 'c';
	private static final char numPlaseHolder = 'n';

	public static boolean doExactMatch(String comparason, String data) {
		
		boolean matched = false;
		
		for (int x = 0; x < comparason.length(); x++) {
			if (comparason.charAt(x) == charPlaseHolder) {
				if (Strings.isAlfabetical(data.charAt(x))) {
					matched = true;
				}else{
					matched = false;
				}
			} else if(comparason.charAt(x) == numPlaseHolder){
				if (Strings.isNumerical(data.charAt(x))) {
					matched = true;
				}else{
					matched = false;
				}
			} else {
				if(comparason.charAt(x) == data.charAt(x))
					matched = true;
				else
					matched = false;
			}
		}
		return matched;
		
	}

}
