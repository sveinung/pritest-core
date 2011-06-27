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

package no.pritest.prioritization.methodcoverage.algorithm;

import no.pritest.prioritization.methodcoverage.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MethodCoverageAlgorithmTest {

	private Map<String,ClassCover> sourceMethodCoverage;
	private Map<String,ClassCover> testSuiteMethodCoverage;

	@Before
	public void setup() {
		setupTestSuite();
        setupSource();
	}

	private void setupSource() {
		sourceMethodCoverage = new HashMap<String, ClassCover>();
        
        ClassCover classA = new ClassCover("A", "no.citrus");
        List<ProcessedMethodCall> methodCallsAa = new ArrayList<ProcessedMethodCall>();
        MethodCover methodCoverAa = new MethodCover("A", "void", "a", new ArrayList<ReferenceType>(), methodCallsAa);
        classA.getMethods().put(MethodCover.createUniqueMapKey(methodCoverAa), methodCoverAa);
        sourceMethodCoverage.put(classA.getName(), classA);
        
        ClassCover classB = new ClassCover("B", "no.citrus");
        List<ProcessedMethodCall> methodCallsBb = new ArrayList<ProcessedMethodCall>();
        MethodCover methodCoverBb = new MethodCover("B", "void", "b", new ArrayList<ReferenceType>(), methodCallsBb);
        classB.getMethods().put(MethodCover.createUniqueMapKey(methodCoverBb), methodCoverBb);
        sourceMethodCoverage.put(classB.getName(), classB);
        
        ClassCover classC = new ClassCover("C", "no.citrus");
        List<ProcessedMethodCall> methodCallsCc = new ArrayList<ProcessedMethodCall>();
        MethodCover methodCoverCc = new MethodCover("C", "void", "c", new ArrayList<ReferenceType>(), methodCallsCc);
        classC.getMethods().put(MethodCover.createUniqueMapKey(methodCoverCc), methodCoverCc);
        sourceMethodCoverage.put(classC.getName(), classC);
        
        ClassCover classD = new ClassCover("D", "no.citrus");
        List<ProcessedMethodCall> methodCallsDd = new ArrayList<ProcessedMethodCall>();
        MethodCover methodCoverDd = new MethodCover("D", "void", "d", new ArrayList<ReferenceType>(), methodCallsDd);
        classD.getMethods().put(MethodCover.createUniqueMapKey(methodCoverDd), methodCoverDd);
        sourceMethodCoverage.put(classD.getName(), classD);
        
        ClassCover classE = new ClassCover("E", "no.citrus");
        List<ProcessedMethodCall> methodCallsEe = new ArrayList<ProcessedMethodCall>();
        MethodCover methodCoverEe = new MethodCover("E", "void", "e", new ArrayList<ReferenceType>(), methodCallsEe);
        classE.getMethods().put(MethodCover.createUniqueMapKey(methodCoverEe), methodCoverEe);
        sourceMethodCoverage.put(classE.getName(), classE);
        
        ClassCover classF = new ClassCover("F", "no.citrus");
        List<ProcessedMethodCall> methodCallsFf = new ArrayList<ProcessedMethodCall>();
        MethodCover methodCoverFf = new MethodCover("F", "void", "f", new ArrayList<ReferenceType>(), methodCallsFf);
        classF.getMethods().put(MethodCover.createUniqueMapKey(methodCoverFf), methodCoverFf);
        sourceMethodCoverage.put(classF.getName(), classF);
	}

	private void setupTestSuite() {
		testSuiteMethodCoverage = new HashMap<String, ClassCover>();
		
        ClassCover testCase1 = new ClassCover("test1", "no.citrus");
        MethodCover methodCover1A = new MethodCover("test1", "void", "should_do_1", new ArrayList<ReferenceType>(), new ArrayList<ProcessedMethodCall>());
        methodCover1A.getMethodCalls().add(new ProcessedMethodCall("A", "a", new ArrayList<String>()));
        methodCover1A.getMethodCalls().add(new ProcessedMethodCall("D", "d", new ArrayList<String>()));
        methodCover1A.getMethodCalls().add(new ProcessedMethodCall("E", "e", new ArrayList<String>()));
        testCase1.getMethods().put(MethodCover.createUniqueMapKey(methodCover1A), methodCover1A);
		testSuiteMethodCoverage.put(testCase1.getName(), testCase1);
		
		ClassCover testCase2 = new ClassCover("test2", "no.citrus");
        MethodCover methodCover2A = new MethodCover("test2", "void", "should_do_2", new ArrayList<ReferenceType>(), new ArrayList<ProcessedMethodCall>());
        methodCover2A.getMethodCalls().add(new ProcessedMethodCall("B", "b", new ArrayList<String>()));
        methodCover2A.getMethodCalls().add(new ProcessedMethodCall("A", "a", new ArrayList<String>()));
        methodCover2A.getMethodCalls().add(new ProcessedMethodCall("D", "d", new ArrayList<String>()));
        methodCover2A.getMethodCalls().add(new ProcessedMethodCall("E", "e", new ArrayList<String>()));
        methodCover2A.getMethodCalls().add(new ProcessedMethodCall("F", "f", new ArrayList<String>()));
		testCase2.getMethods().put(MethodCover.createUniqueMapKey(methodCover2A), methodCover2A);
		testSuiteMethodCoverage.put(testCase2.getName(), testCase2);
		
		ClassCover testCase3 = new ClassCover("test3", "no.citrus");
        MethodCover methodCover3A = new MethodCover("test3", "void", "should_do_3", new ArrayList<ReferenceType>(), new ArrayList<ProcessedMethodCall>());
        methodCover3A.getMethodCalls().add(new ProcessedMethodCall("C", "c", new ArrayList<String>()));
		testCase3.getMethods().put(MethodCover.createUniqueMapKey(methodCover3A), methodCover3A);
		testSuiteMethodCoverage.put(testCase3.getName(), testCase3);
		
		ClassCover testCase4 = new ClassCover("test4", "no.citrus");
        MethodCover methodCover4A = new MethodCover("test4", "void", "should_do_4", new ArrayList<ReferenceType>(), new ArrayList<ProcessedMethodCall>());
        methodCover4A.getMethodCalls().add(new ProcessedMethodCall("A", "a", new ArrayList<String>()));
        methodCover4A.getMethodCalls().add(new ProcessedMethodCall("D", "d", new ArrayList<String>()));
		testCase4.getMethods().put(MethodCover.createUniqueMapKey(methodCover4A), methodCover4A);
		testSuiteMethodCoverage.put(testCase4.getName(), testCase4);
		
		ClassCover testCase5 = new ClassCover("test5", "no.citrus");
        MethodCover methodCover5A = new MethodCover("test5", "void", "should_do_5", new ArrayList<ReferenceType>(), new ArrayList<ProcessedMethodCall>());
        methodCover5A.getMethodCalls().add(new ProcessedMethodCall("A", "a", new ArrayList<String>()));
        methodCover5A.getMethodCalls().add(new ProcessedMethodCall("B", "b", new ArrayList<String>()));
        methodCover5A.getMethodCalls().add(new ProcessedMethodCall("D", "d", new ArrayList<String>()));
        methodCover5A.getMethodCalls().add(new ProcessedMethodCall("F", "f", new ArrayList<String>()));
		testCase5.getMethods().put(MethodCover.createUniqueMapKey(methodCover5A), methodCover5A);
		testSuiteMethodCoverage.put(testCase5.getName(), testCase5);
	}
	
    @Test
    public void should_support_total_method_coverage() {
        List<SummarizedTestCase> totalMethodCoverage = MethodCoverageAlgorithm.totalMethodCoverage(testSuiteMethodCoverage, sourceMethodCoverage);
        
        assertThat(totalMethodCoverage.get(0).getTestCase().getName(), is(equalTo("test2")));
        assertThat(totalMethodCoverage.get(1).getTestCase().getName(), is(equalTo("test5")));
        assertThat(totalMethodCoverage.get(2).getTestCase().getName(), is(equalTo("test1")));
        assertThat(totalMethodCoverage.get(3).getTestCase().getName(), is(equalTo("test4")));
        assertThat(totalMethodCoverage.get(4).getTestCase().getName(), is(equalTo("test3")));
    }
    
    @Test
    public void should_support_additional_method_coverage() {
    	List<SummarizedTestCase> additionalMethodCoverage = MethodCoverageAlgorithm.additionalMethodCoverage(testSuiteMethodCoverage, sourceMethodCoverage);
    	
    	assertThat(additionalMethodCoverage.get(0).getTestCase().getName(), is(equalTo("test2")));
        assertThat(additionalMethodCoverage.get(1).getTestCase().getName(), is(equalTo("test3")));
        assertThat(additionalMethodCoverage.get(2).getTestCase().getName(), is(equalTo("test5")));
        assertThat(additionalMethodCoverage.get(3).getTestCase().getName(), is(equalTo("test1")));
        assertThat(additionalMethodCoverage.get(4).getTestCase().getName(), is(equalTo("test4")));
    }
    
    @Test
    public void should_support_test_cases_not_covering_any_methods() {
    	ClassCover testCase6 = new ClassCover("test6", "no.citrus");
        MethodCover methodCover6A = new MethodCover("test6", "void", "should_do_6", new ArrayList<ReferenceType>(), new ArrayList<ProcessedMethodCall>());
        testCase6.getMethods().put(MethodCover.createUniqueMapKey(methodCover6A), methodCover6A);
		testSuiteMethodCoverage.put(testCase6.getName(), testCase6);
    	
    	List<SummarizedTestCase> additionalMethodCoverage = MethodCoverageAlgorithm.additionalMethodCoverage(testSuiteMethodCoverage, sourceMethodCoverage);
    	
    	assertThat(additionalMethodCoverage.get(5).getTestCase().getName(), is(equalTo("test6")));
    }
    
    @Test
    public void should_support_uncovered_methods() {
    	ClassCover classZ = new ClassCover("Z", "no.citrus");
        List<ProcessedMethodCall> methodCallsZz = new ArrayList<ProcessedMethodCall>();
        MethodCover methodCoverZz = new MethodCover("Z", "void", "z", new ArrayList<ReferenceType>(), methodCallsZz);
        classZ.getMethods().put(MethodCover.createUniqueMapKey(methodCoverZz), methodCoverZz);
        sourceMethodCoverage.put(classZ.getName(), classZ);
        
    	List<SummarizedTestCase> additionalMethodCoverage = MethodCoverageAlgorithm.additionalMethodCoverage(testSuiteMethodCoverage, sourceMethodCoverage);
    	
    	assertThat(additionalMethodCoverage.get(0).getTestCase().getName(), is(equalTo("test2")));
        assertThat(additionalMethodCoverage.get(1).getTestCase().getName(), is(equalTo("test3")));
        assertThat(additionalMethodCoverage.get(2).getTestCase().getName(), is(equalTo("test5")));
        assertThat(additionalMethodCoverage.get(3).getTestCase().getName(), is(equalTo("test1")));
        assertThat(additionalMethodCoverage.get(4).getTestCase().getName(), is(equalTo("test4")));
    }
}
