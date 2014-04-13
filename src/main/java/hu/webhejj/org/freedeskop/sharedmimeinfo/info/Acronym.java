package hu.webhejj.org.freedeskop.sharedmimeinfo.info;

public class Acronym extends Text {

	private String expanded;

	public String getExpanded() {
		return expanded;
	}

	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)", getValue(), expanded);
	}
}
