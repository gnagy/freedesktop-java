package hu.webhejj.org.freedeskop.sharedmimeinfo.info;

public class RootXml {

	private String namespaceURI;
	private String localName;

	public String getNamespaceURI() {
		return namespaceURI;
	}
	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	
	@Override
	public String toString() {
		return namespaceURI + " / " + localName;
	}
}
