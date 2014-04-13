package hu.webhejj.org.freedeskop.sharedmimeinfo.match;

import hu.webhejj.commons.trie.TrieNode;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.Glob;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MimeType;
import hu.webhejj.org.freedeskop.sharedmimeinfo.match.GlobCompiler.GlobType;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlobMatcher {
	
	private TrieNode<GlobMatcherEntry> root = new TrieNode<GlobMatcherEntry>(20, null);
	private TrieNode<GlobMatcherEntry> rootCaseSensitive = new TrieNode<GlobMatcherEntry>(10, null);
	private List<GlobMatcherEntry> regexGlobs = new ArrayList<GlobMatcherEntry>();
	private List<GlobMatcherEntry> regexGlobsCaseSensitive = new ArrayList<GlobMatcherEntry>();

	
	private GlobMatcherResultComparator resultSorter = new GlobMatcherResultComparator();

	
	public void addGlobs(MimeType mimeType) {
		for(Glob glob: mimeType.getGlobs()) {
			addGlob(mimeType, glob);
		}
	}
	
	public void addGlob(MimeType mimeType, Glob glob) {
		
		GlobType globType = GlobCompiler.getGlobType(glob.getPattern());
		
		
		String compiled = null;
		switch(globType) {
		case ENDING: compiled = GlobCompiler.globToEnding(glob.getPattern()); break;
		case EXACT: compiled = glob.getPattern(); break;
		case REGEX: compiled = GlobCompiler.globToRegex(glob.getPattern()); break;
		}
		
		if(!glob.isCaseSensitive()) {
			compiled = compiled.toLowerCase();
		}
		
		GlobMatcherEntry value = new GlobMatcherEntry(glob, globType, compiled, mimeType);
		
		if(globType == GlobType.REGEX) {
			if(glob.isCaseSensitive()) {
				regexGlobsCaseSensitive.add(value);
			} else {
				regexGlobs.add(value);
			}
		} else {
			addToTrie(glob.isCaseSensitive() ? rootCaseSensitive : root, value);
		}
	}
	
	public List<GlobMatcherEntry> match(String string) {
		
		List<GlobMatcherEntry> matches = new ArrayList<GlobMatcherEntry>();
		String lowerCase = string.toLowerCase();
		
		matchInTrie(root, matches, lowerCase);
		matchInTrie(rootCaseSensitive, matches, string);
		
		for (GlobMatcherEntry entry : regexGlobs) {
			if(entry.getPattern().matcher(lowerCase).matches()) {
				matches.add(entry);
			}
		}
		for (GlobMatcherEntry entry : regexGlobsCaseSensitive) {
			if(entry.getPattern().matcher(string).matches()) {
				matches.add(entry);
			}
		}		
		
		Collections.sort(matches, resultSorter);
		return matches;
	}
	
	protected void matchInTrie(TrieNode<GlobMatcherEntry> node, List<GlobMatcherEntry> matches, String string) {

		for(int i = string.length() - 1; i >= 0; i--) {
			
			char c = string.charAt(i);
			node = node.getChild(c);
			
			if(node == null) {
				break;
			}
			if(node.getValue() != null) {
				GlobType globType = node.getValue().getGlobType();
				if(globType == GlobType.ENDING || (globType == GlobType.EXACT && i == 0)) {
					matches.add(node.getValue());
				}
			}
		}		
	}


	protected void addToTrie(TrieNode<GlobMatcherEntry> node, GlobMatcherEntry value) {
		
		for(int i = value.getCompiled().length() - 1; i >= 0; i--) {
			char c = value.getGlob().isCaseSensitive()
					? value.getCompiled().charAt(i)
					: Character.toLowerCase(value.getCompiled().charAt(i));
			node = node.addChild(c);
			if(i == 0) {
				node.setValue(value);
			}
		}
	}
	
	public void dump() {
		root.dump(0);
		for (GlobMatcherEntry entry : regexGlobs) {
			System.out.println(entry);
		} 
	}
	
	public void toJSON(Writer writer) throws IOException {

		writer.write("{");

		writer.write("\"trie\":{");
		toJSON(writer, root);
		writer.write("},");

		writer.write("\"trieCS\":{");
		toJSON(writer, rootCaseSensitive);
		writer.write("},");
		
		writer.write("\"regex\":[");
		for(int i = 0; i < regexGlobs.size(); i++) {
			writer.write("{\"pattern\"=\"");
			writer.write(regexGlobs.get(i).getPattern().pattern());
			writer.write("\",mimeType:\"");
			writer.write(regexGlobs.get(i).getMimeType().getName());
			writer.write('"');
			if(i < regexGlobs.size() -1) {
				writer.write(',');
			}
		}
		writer.write("],");
		
		writer.write("\"regexCS\":[");
		for(int i = 0; i < regexGlobsCaseSensitive.size(); i++) {
			writer.write("{\"pattern\":\"");
			writer.write(regexGlobsCaseSensitive.get(i).getPattern().pattern());
			writer.write("\",\"mimeType\":\"");
			writer.write(regexGlobsCaseSensitive.get(i).getMimeType().getName());
			writer.write("\"}");
			if(i < regexGlobsCaseSensitive.size() -1) {
				writer.write(',');
			}
		}
		writer.write("]");

		writer.write("}");
	}
	
	protected void toJSON(Writer writer, TrieNode<GlobMatcherEntry> trieNode) throws IOException {
		
		if(trieNode == null) {
			return;
		}
		if(trieNode.getValue() != null) {
			writer.write("\"mimeType\":\"");
			writer.write(trieNode.getValue().getMimeType().getName());
			writer.write("\",");
			writer.write("\"type\":\"");
			writer.write(trieNode.getValue().getGlobType().name());
			writer.write("\",");
		}
		writer.write("\"children\": {");
		for(int i = 0; i < trieNode.getSize(); i++) {
			char key = trieNode.getKeys()[i];
			writer.write('"');
			writer.write(key);
			writer.write('"');
			writer.write(":{");
			if(trieNode.getChild(key) != null) {
				toJSON(writer, trieNode.getChild(key));
			}
			writer.write("}");
			if(i < trieNode.getSize() - 1) {
				writer.write(',');
			}
		}
		writer.write("}");
	}
}
