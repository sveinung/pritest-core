/**
    This file is part of Pritest.

    Pritest is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Pritest is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/

package no.pritest.util;

import java.io.File;

public class TestCaseMapper {

	private final static String JAVA_SUFFIX = ".java";
	
	private final String[] folders;

	public TestCaseMapper(String[] folders) {
		this.folders = folders;
	}

	public String parseFilePathToPackage(String fileName) {
		String packageName = null;
		
		String pathPartToRemove = removePathPart(fileName);
		
		if (pathPartToRemove != null && fileName.endsWith(JAVA_SUFFIX)) {
			
			packageName = fileName.substring(pathPartToRemove.length(), fileName.length() - JAVA_SUFFIX.length());
			packageName = trimDirectorySeparator(packageName);
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

	private String trimDirectorySeparator(String packageName) {
		if (packageName.startsWith(File.separator)) {
			packageName = packageName.substring(1);
		}
		return packageName;
	}

	public static String toTestCase(String className) {
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
		String testCaseName = toTestCase(className);
		
		return testCaseName;
	}

}
