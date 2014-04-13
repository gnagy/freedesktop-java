package hu.webhejj.org.freedeskop.sharedmimeinfo.io;

import hu.webhejj.org.freedeskop.sharedmimeinfo.info.Acronym;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.Glob;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.Magic;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MagicMatch;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MimeType;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.RootXml;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.Text;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.TreeMagicMatch;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MagicMatch.MagicMatchType;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.TreeMagicMatch.TreeMagicMatchType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class MimeInfoContentHandler implements ContentHandler {

	private Locator locator;
	private List<MimeType> mimeTypes;
	private MimeType mimeType;
	private Stack<Object> objects;
	private StringBuilder buf;
	
	@Override
	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}

	@Override
	public void startDocument() throws SAXException {
		mimeTypes = new ArrayList<MimeType>();
		buf = new StringBuilder(100);
		objects = new Stack<Object>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

		if(buf.length() > 0) {
			System.out.println("Uh-oh, text buffer not empty when starting element, contains: " + buf.toString());
		}
		if("mime-type".equals(localName)) {
			mimeType = new MimeType();
			mimeTypes.add(mimeType);
			mimeType.setName(atts.getValue("type"));
		
		} else if ("comment".equals(localName)) {
			Text text = new Text();
			text.setLanguage(atts.getValue("xml:lang"));
			objects.push(text);
			mimeType.addComment(text);

		} else if ("acronym".equals(localName) || "expanded-acronym".equals(localName)) {
			String lang = atts.getValue("xml:lang");
			objects.push(getAcronym(lang));

		} else if ("generic-icon".equals(localName)) {
			mimeType.setGenericIcon(atts.getValue("name"));
			
		} else if ("glob".equals(localName)) {
			Glob glob = new Glob();
			glob.setPattern(atts.getValue("pattern"));
			glob.setWeight(parseInt(atts.getValue("weight"), 50));
			glob.setCaseSensitive(parseBoolean(atts.getValue("case-sensitive"), false));
			mimeType.getGlobs().add(glob);
		
		} else if ("magic".equals(localName)) {
			Magic<MagicMatch> magic = new Magic<MagicMatch>();
			magic.setPriority(parseInt(atts.getValue("priority"), 50));
			objects.push(magic);
			mimeType.getMagics().add(magic);

		} else if ("match".equals(localName)) {
			MagicMatch match = new MagicMatch();
			match.setType(MagicMatchType.valueOf(atts.getValue("type").toUpperCase()));
			String[] offset = atts.getValue("offset").split(":");
			match.setOffsetStart(Integer.parseInt(offset[0]));
			match.setOffsetEnd(offset.length > 1 ? Integer.parseInt(offset[1]) : -1);
			match.setValue(atts.getValue("value"));
			match.setMask(atts.getValue("mask"));
			
			Object parent = objects.peek();
			if (parent instanceof Magic<?>) {
				((Magic) parent).getMatches().add(match);

			} else if (parent instanceof MagicMatch) {
				((MagicMatch) parent).getMatches().add(match);
			} else {
				throw new RuntimeException("Could not add MagicMatch, parent was " + parent);
			}
			objects.push(match);

		} else if ("treemagic".equals(localName)) {
			Magic<TreeMagicMatch> magic = new Magic<TreeMagicMatch>();
			magic.setPriority(parseInt(atts.getValue("priority"), 50));
			objects.push(magic);
			mimeType.getTreeMagics().add(magic);

		} else if ("treematch".equals(localName)) {
			TreeMagicMatch match = new TreeMagicMatch();
			match.setType(TreeMagicMatchType.valueOf(atts.getValue("type").toUpperCase()));
			match.setPath(atts.getValue("path"));
			match.setCaseSensitive(parseBoolean(atts.getValue("match-case"), true));
			match.setExecutable(parseBoolean(atts.getValue("executable"), false));
			match.setNonEmpty(parseBoolean(atts.getValue("executable"), false));
			match.setMimeType(atts.getValue("mimetype"));

			Object parent = objects.peek();
			if (parent instanceof Magic<?>) {
				((Magic) parent).getMatches().add(match);

			} else if (parent instanceof TreeMagicMatch) {
				((TreeMagicMatch) parent).getMatches().add(match);
			} else {
				throw new RuntimeException("Could not add MagicMatch, parent was " + parent);
			}
			objects.push(match);

		} else if ("root-XML".equals(localName)) {
			RootXml rootXml = new RootXml();
			rootXml.setLocalName(atts.getValue("namespaceURI"));
			rootXml.setLocalName(atts.getValue("localName"));
			mimeType.setRootXml(rootXml);

		} else if ("alias".equals(localName)) {
			mimeType.getAliases().add(atts.getValue("type"));
			
		} else if ("sub-class-of".equals(localName)) {
			mimeType.getSubTypeOf().add(atts.getValue("type"));
			
		}
	}
	
	protected Acronym getAcronym(String language) {
		Acronym acronym = mimeType.getAcronyms().get(language);
		if(acronym == null) {
			acronym = new Acronym();
			acronym.setLanguage(language);
			mimeType.addAcronym(acronym);
		}
		return acronym;
	}
	
	protected int parseInt(String value, int defaultValue) {
		return value == null ? defaultValue : Integer.parseInt(value);
	}
	protected boolean parseBoolean(String value, boolean defaultValue) {
		return value == null ? defaultValue : Boolean.parseBoolean(value);
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		buf.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
	
		if ("comment".equals(localName)) {
			Text text = (Text) objects.pop();
			text.setValue(buf.toString());
			
		} else if ("acronym".equals(localName)) {
			((Acronym) objects.pop()).setValue(buf.toString());
			// already added to mimeType

		} else if ("expanded-acronym".equals(localName)) {
			((Acronym) objects.pop()).setExpanded(buf.toString());
			// already added to mimeType
		
		} else if ("magic".equals(localName) || "match".equals(localName) 
				|| "treematch".equals(localName) || "treematch".equals(localName)) {
			objects.pop();
		}

		buf.setLength(0);
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
	}

	@Override
	public void endDocument() throws SAXException {
	}
	
	public List<MimeType> getMimeTypes() {
		return mimeTypes;
	}
}
