package hu.webhejj.org.freedeskop.sharedmimeinfo.info;

import java.util.ArrayList;
import java.util.List;

public class TreeMagicMatch {
	
	public static enum TreeMagicMatchType {
		FILE,
		DIRECTORY,
		LINK
	}
	
	private String path;
	private TreeMagicMatchType type;
	private boolean isCaseSensitive;
	private boolean isExecutable;
	private boolean isNonEmpty;
	private String mimeType;
	
	private List<TreeMagicMatch> matches = new ArrayList<TreeMagicMatch>();	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public TreeMagicMatchType getType() {
		return type;
	}
	public void setType(TreeMagicMatchType type) {
		this.type = type;
	}
	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}
	public void setCaseSensitive(boolean isCaseSensitive) {
		this.isCaseSensitive = isCaseSensitive;
	}
	public boolean isExecutable() {
		return isExecutable;
	}
	public void setExecutable(boolean isExecutable) {
		this.isExecutable = isExecutable;
	}
	public boolean isNonEmpty() {
		return isNonEmpty;
	}
	public void setNonEmpty(boolean isNonEmpty) {
		this.isNonEmpty = isNonEmpty;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public List<TreeMagicMatch> getMatches() {
		return matches;
	}
	public void setMatches(List<TreeMagicMatch> matches) {
		this.matches = matches;
	}
	
	
	@Override
	public String toString() {
		return "TreeMatch: " + path;
	}
	
}
