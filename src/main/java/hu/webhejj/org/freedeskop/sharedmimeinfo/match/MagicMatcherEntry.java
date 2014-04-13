package hu.webhejj.org.freedeskop.sharedmimeinfo.match;

import hu.webhejj.org.freedeskop.sharedmimeinfo.info.Magic;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MagicMatch;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MimeType;

public class MagicMatcherEntry {

	private Magic<MagicMatch> magic;
	private MimeType mimeType;

	public MagicMatcherEntry() {
	}
	public MagicMatcherEntry(Magic<MagicMatch> magicMatch, MimeType mimeType) {
		this.magic = magicMatch;
		this.mimeType = mimeType;
	}
	
	public Magic<MagicMatch> getMagic() {
		return magic;
	}
	public void setMagic(Magic<MagicMatch> magicMatch) {
		this.magic = magicMatch;
	}
	
	public MimeType getMimeType() {
		return mimeType;
	}
	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}
	
	@Override
	public String toString() {
		return String.format("%d  %s\n %s", magic.getPriority(), mimeType.getName(), MagicMatch.toString(new StringBuilder(), magic.getMatches()));
	}
}
