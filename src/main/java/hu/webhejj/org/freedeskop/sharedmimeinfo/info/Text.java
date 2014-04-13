package hu.webhejj.org.freedeskop.sharedmimeinfo.info;

public class Text extends Localized {

	private String value;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return getLanguage() + " - " + value;
	}
}
