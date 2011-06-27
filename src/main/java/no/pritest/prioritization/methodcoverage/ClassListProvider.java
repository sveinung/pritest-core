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

package no.pritest.prioritization.methodcoverage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassListProvider {
	private static final String CLASS_SUFFIX = ".class";
	
	public static List<String> getClassList(File dir) {
		List<String> result = new ArrayList<String>();	
		List<File> fileList = getFileList(dir, new String[] {CLASS_SUFFIX});
		String testDir = dir.getAbsolutePath() + "/";
		for(File f : fileList){
			String filePath = f.getAbsolutePath();
			filePath = filePath.replace(testDir, "");
			filePath = filePath.replaceAll("/", ".");
			filePath = filePath.substring(0, filePath.length() - CLASS_SUFFIX.length());
			result.add(filePath);
		}
		return result;
	}
	
	public static List<File> getFileList(File directory, String[] validSuffixes){
		List<File> list = new ArrayList<File>();
		if(directory.isDirectory())
		{
			for(File f : directory.listFiles()){
				if(f.isDirectory()){
					list.addAll(getFileList(f, validSuffixes));
				}
				else{
					for(String suffix : validSuffixes){
						if(f.getName().endsWith(suffix))
						{
							list.add(f);
							break;
						}
					}
					
				}
			}
		}
		return list;
	}
}
