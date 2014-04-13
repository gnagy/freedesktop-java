package hu.webhejj.org.freedeskop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Env {

	private Map<String, String> env;

	public Env() {
		env = System.getenv();
	}
	
	public String get(String key) {
		return env.get(key);
	}

	public String getResolved(String key) {
		return resolve(env.get(key));
	}
	
	
	public File getFile(String key, File defaultValue) {
		
		String value = getResolved(key);
		if(value == null) {
			return defaultValue;
		}
		return new File(value);
	}

	public List<File> getFiles(String key, List<File> defaultValue) {
		
		String value = getResolved(key);
		if(value == null) {
			return defaultValue;
		}
		
		String[] splitValues = value.split(SystemProperties.getPathSeparator());
		List<File> files = new ArrayList<File>(splitValues.length); 
		for(String splitValue: splitValues) {
			files.add(new File(splitValue));
		}		
		
		return files;
	}
	
	public String resolve(String value) {
		return value;
	}
}
