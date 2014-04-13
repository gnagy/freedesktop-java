package hu.webhejj.org.freedeskop.sharedmimeinfo.info;

import java.util.ArrayList;
import java.util.List;

public class MagicMatch {
	
	public static enum MagicMatchType {
		STRING,
		HOST16,
		HOST32,
		BIG16,
		BIG32,
		LITTLE16,
		LITTLE32,
		BYTE,
	}

	private int offsetStart;
	private int offsetEnd;
	private MagicMatchType type;
	private String value;
	private String mask;
	
	private List<MagicMatch> matches = new ArrayList<MagicMatch>();

	public int getOffsetStart() {
		return offsetStart;
	}
	public void setOffsetStart(int offsetStart) {
		this.offsetStart = offsetStart;
	}
	
	public int getOffsetEnd() {
		return offsetEnd;
	}
	public void setOffsetEnd(int offsetEnd) {
		this.offsetEnd = offsetEnd;
	}
	
	public MagicMatchType getType() {
		return type;
	}
	public void setType(MagicMatchType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}

	public List<MagicMatch> getMatches() {
		return matches;
	}
	public void setMatches(List<MagicMatch> matches) {
		this.matches = matches;
	}
	
	@Override
	public String toString() {
		return toString(new StringBuilder()).toString();
	}
	
	public StringBuilder toString(StringBuilder buf) {

		buf.append("Match ");
		buf.append(type);
		buf.append(" [");
		buf.append(offsetStart);
		if(offsetEnd > 0) {
			buf.append(":");
			buf.append(offsetEnd);
		}
		buf.append("] ");
		buf.append(value);
		
		if(matches.size() > 0) {
			buf.append("\n AND ");
			toString(buf, matches);
		}
				
		return buf;
	}
	
	public static StringBuilder toString(StringBuilder buf, List<MagicMatch> matches) {
		if(matches != null) {
			int i = 0;
			for(MagicMatch match: matches) {
				match.toString(buf);
				if(i > 0 || i < matches.size() - 1) {
					buf.append("\n OR ");
				}
			}
		}
		return buf;
	}
}
