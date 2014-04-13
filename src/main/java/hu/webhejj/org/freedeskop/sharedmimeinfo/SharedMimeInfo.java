package hu.webhejj.org.freedeskop.sharedmimeinfo;

import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MimeType;
import hu.webhejj.org.freedeskop.sharedmimeinfo.io.SharedMimeInfoReader;
import hu.webhejj.org.freedeskop.sharedmimeinfo.match.GlobMatcher;
import hu.webhejj.org.freedeskop.sharedmimeinfo.match.GlobMatcherEntry;
import hu.webhejj.org.freedeskop.sharedmimeinfo.match.MagicMacher;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedMimeInfo {

	private Map<String, MimeType> mimeTypeMap = new HashMap<String, MimeType>();
	
	protected GlobMatcher globMatcher;
	protected MagicMacher magicMatcher;

	public static SharedMimeInfo create(InputStream... mimeInfoData) {

		SharedMimeInfo mimeInfo = new SharedMimeInfo();
		mimeInfo.globMatcher = new GlobMatcher();
		mimeInfo.magicMatcher = new MagicMacher();
		
		SharedMimeInfoReader reader = new SharedMimeInfoReader();

		for(InputStream data: mimeInfoData) {
			for(MimeType mimeType: reader.read(data)) {
				mimeInfo.mimeTypeMap.put(mimeType.getName(), mimeType);
				mimeInfo.globMatcher.addGlobs(mimeType);
				mimeInfo.magicMatcher.addMagics(mimeType);
			}
		}

		return mimeInfo;
	}
	
	public MimeType getMimeType(String name) {
		return mimeTypeMap.get(name);
	}

	public List<GlobMatcherEntry> matchGlob(File file) {
		return matchGlob(file.getName());
	}

	public List<GlobMatcherEntry> matchGlob(String fileName) {
		return globMatcher.match(fileName);
	}
	
	public GlobMatcher getGlobMatcher() {
		return globMatcher;
	}
}
