package hu.webhejj.org.freedeskop.sharedmimeinfo.match;

import hu.webhejj.org.freedeskop.sharedmimeinfo.info.Magic;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MagicMatch;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MimeType;

import java.util.ArrayList;
import java.util.List;

public class MagicMacher {

	private List<MagicMatcherEntry> entries = new ArrayList<MagicMatcherEntry>();
	
	public void addMagics(MimeType mimeType) {
		for(Magic<MagicMatch> magic: mimeType.getMagics()) {
			addMagic(mimeType, magic);
		}
	}
	
	public void addMagic(MimeType mimeType, Magic<MagicMatch> magic) {
		MagicMatcherEntry entry = new MagicMatcherEntry(magic, mimeType);
		entries.add(entry);
		
		// System.out.println(entry);
	}
}
