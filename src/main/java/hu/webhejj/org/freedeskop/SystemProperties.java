package hu.webhejj.org.freedeskop;

import java.io.File;

public class SystemProperties {

	
	
	/** Current working directory */
	public static final String PROP_USER_DIR = "user.dir";

	/** User home directory */
	public static final String PROP_USER_HOME = "user.home";

	/** User account name */
	public static final String PROP_USER_NAME = "user.name";
	
	public static final String PROP_PATH_SEPARATOR = "path.separator";

	
	public static String get(String key) {
		return System.getProperty(key);
	}

	public static String get(String key, String defaultValue) {
		return System.getProperty(key, defaultValue);
	}
	
	public static File getCurrentWorkingDirecotry() {
		return new File(get(PROP_USER_DIR));
	}
	
	public static File getUserHome() {
		return new File(get(PROP_USER_HOME));
	}

	public static String getUserName() {
		return get(PROP_USER_NAME);
	}

	public static String getPathSeparator() {
		return get(PROP_PATH_SEPARATOR);
	}
}
