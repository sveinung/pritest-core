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
import no.pritest.prioritization.methodcoverage.model.SummarizedTestCase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MethodCoverage {
    protected List<String> testCases;
	private Map<String, ClassCover> sourceMethodCoverage;
	private Map<String, ClassCover> testSuiteMethodCoverage;
	private Map<String, ClassType> projectSourceClassTypes;

    public MethodCoverage() {
        testCases = new ArrayList<String>();
    }
    
    protected void prioritizeTestCases(String pathToProjectSource, String pathToTestSuite) throws ParseException, IOException {
    	retrieveClassCoverage(pathToProjectSource);
		retrieveTestClassCoverage(pathToTestSuite);
    }

    private void retrieveClassCoverage(String pathToProjectSource)
			throws ParseException, IOException {

		List<File> fileList =
			ClassListProvider.getFileList(new File(pathToProjectSource), new String[] {".java"});

		List<CompilationUnit> compilationUnits = CompilationUnitProvider.getCompilationUnits(fileList);

		ClassTypeProvider classTypeProvider = new ClassTypeProvider(compilationUnits);
		projectSourceClassTypes = classTypeProvider.getClassTypes();

		MethodCoverageProvider mcp = new MethodCoverageProvider(projectSourceClassTypes, compilationUnits);

		sourceMethodCoverage = mcp.getMethodCoverage();
	}
    
    private void retrieveTestClassCoverage(String pathToTestSuiteSource)
			throws ParseException, IOException {
	
		List<File> fileList =
			ClassListProvider.getFileList(new File(pathToTestSuiteSource), new String[] {".java"});
		
		List<CompilationUnit> compilationUnits = CompilationUnitProvider.getCompilationUnits(fileList);
		
		ClassTypeProvider classTypeProvider = new ClassTypeProvider(compilationUnits);
		Map<String, ClassType> classTypes = classTypeProvider.getClassTypes();
		
		Map<String, ClassType> projectSourceAndTestSuite = new HashMap<String, ClassType>();
		projectSourceAndTestSuite.putAll(classTypes);
		projectSourceAndTestSuite.putAll(projectSourceClassTypes);
		
		MethodCoverageProvider mcp = new MethodCoverageProvider(projectSourceAndTestSuite, compilationUnits);
		
		testSuiteMethodCoverage = mcp.getMethodCoverage();
	}
    
    public List<String> getTestCases() {
		return testCases;
	}

	protected Map<String, ClassCover> getSourceMethodCoverage() {
		return sourceMethodCoverage;
	}

	protected Map<String, ClassCover> getTestSuiteMethodCoverage() {
		return testSuiteMethodCoverage;
	}

	protected void addTestCase(SummarizedTestCase summarizedTestCase) {
		ClassCover testCase = summarizedTestCase.getTestCase();
		testCases.add(testCase.getPackageName() + "." + testCase.getName());
	}

}
