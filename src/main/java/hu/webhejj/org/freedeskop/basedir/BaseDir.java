package hu.webhejj.org.freedeskop.basedir;

import hu.webhejj.commons.collections.CollectionUtils;
import hu.webhejj.org.freedeskop.Env;
import hu.webhejj.org.freedeskop.SystemProperties;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BaseDir {
	
	public static final String ENV_DATA_HOME = "XDG_DATA_HOME";
	public static final String ENV_CONFIG_HOME = "XDG_CONFIG_HOME";
	public static final String ENV_DATA_DIRS = "XDG_DATA_DIRS";
	public static final String ENV_CONFIG_DIRS = "XDG_CONFIG_DIRS";
	public static final String ENV_CACHE_HOME = "XDG_CACHE_HOME";
	

	private File dataHome;
	private File configHome;
	private List<File> dataDirs;
	private List<File> allDataDirs;
	private List<File> configDirs;
	private List<File> allConfigDirs;
	private File cacheHome;
	
	public BaseDir() {
		this(new Env());
	}
	
	@SuppressWarnings("unchecked")
	public BaseDir(Env env) {
		File userHome = SystemProperties.getUserHome();
		dataHome = env.getFile(ENV_DATA_HOME, new File(userHome, ".local/share/"));
		configHome = env.getFile(ENV_DATA_HOME, new File(userHome, ".config/"));
		dataDirs = env.getFiles(ENV_DATA_DIRS, Arrays.asList(new File("/usr/local/share/"), new File("/usr/share/")));
		allDataDirs = CollectionUtils.asFlatList(Collections.singletonList(dataHome), dataDirs);
		configDirs = env.getFiles(ENV_DATA_DIRS, Arrays.asList(new File("/etc/xdg")));
		allConfigDirs = CollectionUtils.asFlatList(Collections.singletonList(configHome), configDirs);
		cacheHome =  env.getFile(ENV_DATA_HOME, new File(userHome, ".cache/"));
	}
	
	public File getDataHome() {
		return dataHome;
	}

	public File getConfigHome() {
		return configHome;
	}

	public List<File> getDataDirs() {
		return dataDirs;
	}
	
	public List<File> getAllDataDirs() {
		return allDataDirs;
	}

	public List<File> getConfigDirs() {
		return configDirs;
	}
	
	public List<File> getAllConfigDirs() {
		return allConfigDirs;
	}

	public File getCacheHome() {
		return cacheHome;
	}
	
	public static void main(String[] args) {
		BaseDir baseDir = new BaseDir();
		System.out.println(baseDir.getDataHome());
		System.out.println(baseDir.getConfigHome());
		System.out.println(baseDir.getDataDirs());
		System.out.println(baseDir.getAllDataDirs());
		System.out.println(baseDir.getConfigDirs());
		System.out.println(baseDir.getAllConfigDirs());
		System.out.println(baseDir.getCacheHome());
	}
}
