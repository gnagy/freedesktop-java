package hu.webhejj.org.freedeskop.sharedmimeinfo.info;

public class Glob {

	private String pattern;
	private int weight;
	private boolean isCaseSensitive;

	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}
	public void setCaseSensitive(boolean isCaseSensitive) {
		this.isCaseSensitive = isCaseSensitive;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%d, %s)", pattern, weight, isCaseSensitive ? "CS" : "NCS");
	}
}
