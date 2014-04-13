/**
 * 
 */
package hu.webhejj.org.freedeskop.sharedmimeinfo.match;

import java.util.regex.Pattern;

import hu.webhejj.org.freedeskop.sharedmimeinfo.info.Glob;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MimeType;
import hu.webhejj.org.freedeskop.sharedmimeinfo.match.GlobCompiler.GlobType;

public class GlobMatcherEntry {
	
	private Glob glob;
	private GlobType globType;
	private String compiled;
	private Pattern pattern;
	
	private MimeType mimeType;

	public GlobMatcherEntry() {
	}
	
	public GlobMatcherEntry(Glob glob, GlobType globType, String compiled, MimeType mimeType) {
		this.glob = glob;
		this.globType = globType;
		this.compiled = compiled;
		this.mimeType = mimeType;
		
		if(globType == GlobType.REGEX) {
			pattern = Pattern.compile(compiled);
		}
	}

	public Glob getGlob() {
		return glob;
	}
	public void setGlob(Glob glob) {
		this.glob = glob;
	}

	public GlobType getGlobType() {
		return globType;
	}
	public void setGlobType(GlobType globType) {
		this.globType = globType;
	}
	
	public String getCompiled() {
		return compiled;
	}
	public void setCompiled(String compiled) {
		this.compiled = compiled;
	}
	
	public MimeType getMimeType() {
		return mimeType;
	}
	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}

	public Pattern getPattern() {
		return pattern;
	};

	@Override
	public String toString() {
		return String.format("%s  %s  %s  %s", globType, glob, compiled, mimeType.getName());
	}
}