package hu.webhejj.org.freedeskop.sharedmimeinfo.info;

import java.util.ArrayList;
import java.util.List;

public class Magic<T> {
	private int priority;
	private List<T> matches = new ArrayList<T>();

	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public List<T> getMatches() {
		return matches;
	}
	public void setMatches(List<T> matches) {
		this.matches = matches;
	}
}