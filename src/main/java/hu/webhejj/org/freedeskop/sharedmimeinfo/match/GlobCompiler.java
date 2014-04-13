package hu.webhejj.org.freedeskop.sharedmimeinfo.match;

/**
 * 
 * @author greg
 *
 */
public class GlobCompiler {

	public static enum GlobType {
		EXACT,
		ENDING,
		REGEX
	}
	
	public static final int STAR_CANNOT_MATCH_NULL_MASK = 0x0002;
	public static final int QUESTION_MATCHES_ZERO_OR_ONE_MASK = 0x0004;	
	
	public static GlobType getGlobType(String pattern) {
		
		
		if(pattern.length() > 0 && pattern.charAt(0) == '*') {
			for(int i = 1; i < pattern.length(); i++) {
				if(isGlobMetaCharacter(pattern.charAt(i))) {
					return GlobType.REGEX; 
				}
			}
			return GlobType.ENDING;
		}
		
		for(int i = 0; i < pattern.length(); i++) {
			if(isGlobMetaCharacter(pattern.charAt(i))) {
				return GlobType.REGEX; 
			}
		}
		return GlobType.EXACT;
	}
	
	public static String globToEnding(String pattern) {
		if(pattern.length() > 0 && pattern.charAt(0) == '*') {
			return pattern.substring(1);
		}
		return null;
	}
	
	public static String globToRegex(String pattern) {
		return globToRegex(pattern, 0);
	}
	
	// based on jakarta oro GlobCompiler
	public static String globToRegex(String pattern, int options) {
		
		boolean inCharSet;
		boolean starCannotMatchNull;
		boolean questionMatchesZero;
		
		int ch;
		StringBuffer buffer;

		buffer = new StringBuffer(2 * pattern.length());
		inCharSet = false;

		questionMatchesZero = ((options & QUESTION_MATCHES_ZERO_OR_ONE_MASK) != 0);
		starCannotMatchNull = ((options & STAR_CANNOT_MATCH_NULL_MASK) != 0);

		for (ch = 0; ch < pattern.length(); ch++) {
			switch (pattern.charAt(ch)) {
			case '*':
				if (inCharSet)
					buffer.append('*');
				else {
					if (starCannotMatchNull)
						buffer.append(".+");
					else
						buffer.append(".*");
				}
				if(ch > 1) {
				}
				break;
			case '?':
				if (inCharSet)
					buffer.append('?');
				else {
					if (questionMatchesZero)
						buffer.append(".?");
					else
						buffer.append('.');
				}
				break;
			case '[':
				inCharSet = true;
				buffer.append(pattern.charAt(ch));

				if (ch + 1 < pattern.length()) {
					switch (pattern.charAt(ch + 1)) {
					case '!':
					case '^':
						buffer.append('^');
						++ch;
						continue;
					case ']':
						buffer.append(']');
						++ch;
						continue;
					}
				}
				break;
			case ']':
				inCharSet = false;
				buffer.append(pattern.charAt(ch));
				break;
			case '\\':
				buffer.append('\\');
				
				if (ch == pattern.length() - 1) {
					buffer.append('\\');
				
				} else if (isGlobMetaCharacter(pattern.charAt(ch + 1))) {
					buffer.append(pattern.charAt(++ch));
				
				} else {
					buffer.append('\\');
				}
				break;
			default:
				if (!inCharSet && isRegexMetaCharacter(pattern.charAt(ch))) {
					buffer.append('\\');
				}
				buffer.append(pattern.charAt(ch));
				break;
			}
		}

		return buffer.toString();
	}

	private static boolean isRegexMetaCharacter(char ch) {
		return (ch == '*' || ch == '?' || ch == '+' || ch == '[' || ch == ']'
				|| ch == '(' || ch == ')' || ch == '|' || ch == '^'
				|| ch == '$' || ch == '.' || ch == '{' || ch == '}' || ch == '\\');
	}

	private static boolean isGlobMetaCharacter(char ch) {
		return (ch == '*' || ch == '?' || ch == '[' || ch == ']');
	}
	
	public static void main(String[] args) {
		System.out.println(getGlobType("*.exe"));
		System.out.println(globToRegex("*.exe"));
		System.out.println(globToEnding("*.exe"));
	}
}
