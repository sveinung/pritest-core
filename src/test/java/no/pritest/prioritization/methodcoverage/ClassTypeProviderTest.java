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

import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import no.pritest.prioritization.methodcoverage.model.ClassType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class ClassTypeProviderTest {
	
	private ClassTypeProvider classTypeProvider;
	private List<File> projectFiles;
	private List<CompilationUnit> compilationUnits;
	
	@Before
	public void setup() throws ParseException, IOException{
		File projectDirectory = new File("src/main/java/");
		projectFiles = ClassListProvider.getFileList(projectDirectory, new String[]{".java"});
		compilationUnits = CompilationUnitProvider.getCompilationUnits(this.projectFiles);
	}
	
	@Test
	public void projectFiles_should_have_at_least_one_file(){
		assertThat(this.projectFiles.isEmpty(), equalTo(false));
	}
	
	@Test
	public void ensure_that_map_of_classtypes_has_items() throws ParseException, IOException{
		classTypeProvider = new ClassTypeProvider(compilationUnits);
		Map<String, ClassType> classTypes = this.classTypeProvider.getClassTypes();
		assertThat(classTypes.isEmpty(), equalTo(false));
	}
}
