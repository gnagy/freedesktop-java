package hu.webhejj.org.freedeskop.sharedmimeinfo.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MimeType {

	private String name;
	private Map<String, Text> comments = new HashMap<String, Text>();
	private Map<String, Acronym> acronyms = new HashMap<String, Acronym>();
	private String genericIcon;
	private List<Glob> globs = new ArrayList<Glob>();
	private List<Magic<MagicMatch>> magics = new ArrayList<Magic<MagicMatch>>();
	private List<Magic<TreeMagicMatch>> treeMagics = new ArrayList<Magic<TreeMagicMatch>>();
	private RootXml rootXml;
	private List<String> aliases = new ArrayList<String>();
	private List<String> subTypeOf = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Text> getComments() {
		return comments;
	}
	public void setComments(Map<String, Text> comments) {
		this.comments = comments;
	}
	public Map<String, Acronym> getAcronyms() {
		return acronyms;
	}
	public void setAcronyms(Map<String, Acronym> acronyms) {
		this.acronyms = acronyms;
	}
	public String getGenericIcon() {
		return genericIcon;
	}
	public void setGenericIcon(String genericIcon) {
		this.genericIcon = genericIcon;
	}
	public List<Glob> getGlobs() {
		return globs;
	}
	public void setGlobs(List<Glob> globs) {
		this.globs = globs;
	}
	public List<Magic<MagicMatch>> getMagics() {
		return magics;
	}
	public void setMagics(List<Magic<MagicMatch>> magics) {
		this.magics = magics;
	}
	public List<Magic<TreeMagicMatch>> getTreeMagics() {
		return treeMagics;
	}
	public void setTreeMagics(List<Magic<TreeMagicMatch>> treeMagics) {
		this.treeMagics = treeMagics;
	}
	public RootXml getRootXml() {
		return rootXml;
	}
	public void setRootXml(RootXml rootXml) {
		this.rootXml = rootXml;
	}
	public List<String> getAliases() {
		return aliases;
	}
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}
	public List<String> getSubTypeOf() {
		return subTypeOf;
	}
	public void setSubTypeOf(List<String> subTypeOf) {
		this.subTypeOf = subTypeOf;
	}
	
	@Override
	public String toString() {
		return getName() + " - " + getComments().get(null).getValue();
	}
	
	public void addComment(Text comment) {
		comments.put(comment.getLanguage(), comment);
	}

	public void addAcronym(Acronym acronym) {
		acronyms.put(acronym.getLanguage(), acronym);
	}	
}
