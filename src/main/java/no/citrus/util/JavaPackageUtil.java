package no.citrus.util;

import java.io.File;

public class JavaPackageUtil {

	private final static String JAVA_SUFFIX = ".java";
	
	private final String[] folders;

	public JavaPackageUtil(String[] folders) {
		this.folders = folders;
	}

	public String parseFilePathToPackage(String fileName) {
		String packageName = null;
		
		String pathPartToRemove = removePathPart(fileName);
		
		if (pathPartToRemove != null && fileName.endsWith(JAVA_SUFFIX)) {
			
			packageName = fileName.substring(pathPartToRemove.length(), fileName.length() - JAVA_SUFFIX.length());
			packageName = trimDirectorySeperator(packageName);
			packageName = packageName.replace(File.separatorChar, '.');
		}
		
		return packageName;
	}

	private String removePathPart(String fileName) {
		for (String folder : folders) {
			if (fileName.startsWith(folder)) {
				return folder;
			}
		}
		return null;
	}

	private String trimDirectorySeperator(String packageName) {
		if (packageName.startsWith(File.separator)) {
			packageName = packageName.substring(1);
		}
		return packageName;
	}

	public static String makeTestCaseName(String className) {
		if (className != null) {
			if (!className.endsWith("Test")) {
				return className + "Test";
			}
			return className;
		}
		return null;
	}

	public String prepareTestCaseName(String fileName) {
		String className = parseFilePathToPackage(fileName);
		String testCaseName = makeTestCaseName(className);
		
		return testCaseName;
	}

}
