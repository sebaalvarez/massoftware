package org.cendra.environment.vm;

import java.util.ArrayList;
import java.util.List;

public class BinaryPath {

	/** User's current working directory */
	@SuppressWarnings("unused")
	private String userCurrentWorkingDirectory = "unknown";

	// /** Java class path */
	// @SuppressWarnings("unused")
	// private String javaClassPath = "unknown";
	/** List of paths to search when loading libraries */
	@SuppressWarnings("unused")
	private List<String> libraryPath;
	/** Path of extension directory or directories */
	@SuppressWarnings("unused")
	private String extDirs = "unknown";

	/** Java class path */
	@SuppressWarnings("unused")
	private List<String> classPath;

	// public String getJavaClassPath() {
	// return System.getProperty("java.class.path");
	// }
	//
	// public void setJavaClassPath(String javaClassPath) {
	// // this.javaClassPath = javaClassPath;
	// }

	public List<String> getLibraryPath() {
		List<String> r = new ArrayList<String>();

		String s = System.getProperty("java.library.path");

		if (s.contains(";")) {
			String[] array = s.split(";");

			for (String item : array) {
				if (item != null && item.isEmpty() == false) {
					r.add(item);
				}
			}
		} else {
			r.add(s);
		}

		return r;
	}

	public void setLibraryPath(String javaLibraryPath) {
		// this.javaLibraryPath = javaLibraryPath;
	}

	public String getExtDirs() {
		return System.getProperty("java.ext.dirs");
	}

	public void setExtDirs(String javaExtDirs) {
		// this.javaExtDirs = javaExtDirs;
	}

	public List<String> getClassPath() {
		List<String> l = new ArrayList<String>();
		String s = System.getProperty("java.class.path");
		if (s != null) {
			String[] ss = s.split(":");

			for (String e : ss) {
				l.add(e);
			}

		}

		return l;

	}

	public void setClassPath(List<String> binaryPathList) {
		// this.binaryPathList = binaryPathList;
	}

	public String getUserCurrentWorkingDirectory() {
		return System.getProperty("user.dir");
	}

	public void setUserCurrentWorkingDirectory(String dir) {
		// this.dir = dir;
	}

}
