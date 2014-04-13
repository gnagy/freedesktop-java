package hu.webhejj.org.freedeskop.sharedmimeinfo.io;

import hu.webhejj.commons.time.StopWatch;
import hu.webhejj.org.freedeskop.sharedmimeinfo.info.MimeType;
import hu.webhejj.org.freedeskop.sharedmimeinfo.match.GlobMatcher;
import hu.webhejj.org.freedeskop.sharedmimeinfo.match.MagicMacher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SharedMimeInfoReader {

	public List<MimeType> read(InputStream inputStream) {

		try {
			XMLReader parser = XMLReaderFactory.createXMLReader();
			
			MimeInfoContentHandler contentHandler = new MimeInfoContentHandler();
			parser.setContentHandler(contentHandler);
			parser.parse(new InputSource(inputStream));
			
			return contentHandler.getMimeTypes();
		} catch (Exception e) {
			throw new RuntimeException("Error while parsing input stream", e);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		SharedMimeInfoReader reader = new SharedMimeInfoReader();
		
		long start = StopWatch.time();
		List<MimeType> mimeTypes = reader.read(new FileInputStream("/usr/share/mime/packages/freedesktop.org.xml"));
		StopWatch.time("Load finished", start);
		
		GlobMatcher globMatcher = new GlobMatcher();
		MagicMacher magicMatcher = new MagicMacher();
		
		for(MimeType mimeType: mimeTypes) {
			globMatcher.addGlobs(mimeType);
		}
		
		for(MimeType mimeType: mimeTypes) {
			magicMatcher.addMagics(mimeType);
		}
		
		System.out.println(globMatcher.match("README.tar.gz"));
	}
}
