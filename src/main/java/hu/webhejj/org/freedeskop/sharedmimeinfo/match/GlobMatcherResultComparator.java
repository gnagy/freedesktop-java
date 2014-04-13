package hu.webhejj.org.freedeskop.sharedmimeinfo.match;

import java.util.Comparator;

/**
 * Comparator for sorting GlobMatcherEntry items in the matcher result list according to relevance
 * 
 * @author greg
 *
 */
public class GlobMatcherResultComparator implements Comparator<GlobMatcherEntry> {

	@Override
	public int compare(GlobMatcherEntry o1, GlobMatcherEntry o2) {
		int result = o2.getGlob().getWeight() - o1.getGlob().getWeight();
		if(result == 0) {
			result = o2.getCompiled().length() - o1.getCompiled().length();
		}
		
		return result;
	}
}
