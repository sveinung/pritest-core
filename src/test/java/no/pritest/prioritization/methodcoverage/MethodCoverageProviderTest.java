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
import no.pritest.prioritization.methodcoverage.model.ClassCover;
import no.pritest.prioritization.methodcoverage.model.ClassType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class MethodCoverageProviderTest {

	private MethodCoverageProvider methodCoverageProvider;
	private Map<String, ClassType> classTypes;
	private List<CompilationUnit> compilationUnits;
	
	@Before
	public void setup() throws ParseException, IOException{
		File sourceDir = new File("src/main/java");
		List<File> sourceFiles = ClassListProvider.getFileList(sourceDir, new String[]{".java"});
		compilationUnits = CompilationUnitProvider.getCompilationUnits(sourceFiles);
		ClassTypeProvider classTypeProvider = new ClassTypeProvider(compilationUnits);
		classTypes = classTypeProvider.getClassTypes();
	}
	
	@Test
	public void should_retrieve_map_of_methodcoverage_items(){
		this.methodCoverageProvider = new MethodCoverageProvider(classTypes, compilationUnits);
		Map<String, ClassCover> methodCoverageMap = methodCoverageProvider.getMethodCoverage();
		assertThat(methodCoverageMap.isEmpty(), equalTo(false));
	}
}
