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

import no.pritest.prioritization.methodcoverage.MethodCoverageSummarizer;
import no.pritest.prioritization.methodcoverage.model.ClassCover;
import no.pritest.prioritization.methodcoverage.model.MethodCover;
import no.pritest.prioritization.methodcoverage.model.SummarizedTestCase;

import java.util.*;

public class MethodCoverageAlgorithm {
    private static List<SummarizedTestCase> sortTestCasesByCoverage(Map<String, ClassCover> testSuiteMethodCoverage, Map<String, ClassCover> sourceMethodCoverage) {
		List<SummarizedTestCase> prioritizedTestCases = new ArrayList<SummarizedTestCase>();

	    Collection<ClassCover> testCaseCollection = testSuiteMethodCoverage.values();
	    for (ClassCover testCase : testCaseCollection) {
	        MethodCoverageSummarizer mcs = new MethodCoverageSummarizer(sourceMethodCoverage, testCase);
	        Map<String, MethodCover> summarizedCoverage = mcs.getSummarizedCoverage();
	        SummarizedTestCase summarizedTestCase = new SummarizedTestCase(testCase, summarizedCoverage);
	        prioritizedTestCases.add(summarizedTestCase);
	    }

	    Collections.sort(prioritizedTestCases);
	    Collections.reverse(prioritizedTestCases);

		return prioritizedTestCases;
	}

    public static List<SummarizedTestCase> totalMethodCoverage(Map<String, ClassCover> testSuiteMethodCoverage, Map<String, ClassCover> sourceMethodCoverage) {
        return sortTestCasesByCoverage(testSuiteMethodCoverage, sourceMethodCoverage);
    }

    public static List<SummarizedTestCase> additionalMethodCoverage(Map<String, ClassCover> testSuiteMethodCoverage, Map<String, ClassCover> sourceMethodCoverage) {
    	List<SummarizedTestCase> prioritizedTestCases = sortTestCasesByCoverage(testSuiteMethodCoverage, sourceMethodCoverage);
    	List<SummarizedTestCase> results = new ArrayList<SummarizedTestCase>();
    	
    	results.addAll(additionalMethodCoverageHelper(sourceMethodCoverage, prioritizedTestCases));
    	
        return results;
    }

	private static List<SummarizedTestCase> additionalMethodCoverageHelper(
			Map<String, ClassCover> sourceMethodCoverage,
			List<SummarizedTestCase> prioritizedTestCases) {
		
		List<SummarizedTestCase> results = new ArrayList<SummarizedTestCase>();
		
		int amountOfCoveredMethods = coveredMethodsInSource(sourceMethodCoverage);
		
    	while (!prioritizedTestCases.isEmpty() && amountOfCoveredMethods > 0) {
			SummarizedTestCase mostCoveringTestCase = Collections.max(prioritizedTestCases);
			prioritizedTestCases.remove(mostCoveringTestCase);
			
			results.add(mostCoveringTestCase);
			
			Map<String, MethodCover> alreadyCoveredMethods = mostCoveringTestCase.getSummarizedCoverage();
			amountOfCoveredMethods -= mostCoveringTestCase.coveredMethods();
			
			markMethodAsCovered(prioritizedTestCases, alreadyCoveredMethods);
		}
    	
    	if (!prioritizedTestCases.isEmpty() && amountOfCoveredMethods == 0) {
			unMarkCoveredMethods(prioritizedTestCases);
			
			results.addAll(additionalMethodCoverageHelper(sourceMethodCoverage, prioritizedTestCases));
		}
    	
    	return results;
	}

	private static void markMethodAsCovered(
			List<SummarizedTestCase> prioritizedTestCases,
			Map<String, MethodCover> alreadyCoveredMethods) {
		
		for (MethodCover coveredMethod : alreadyCoveredMethods.values()) {
			for (SummarizedTestCase stc : prioritizedTestCases) {
				stc.markMethod(coveredMethod);
			}
		}
	}

	private static void unMarkCoveredMethods(List<SummarizedTestCase> prioritizedTestCases) {
		for (SummarizedTestCase remainingTestCase : prioritizedTestCases) {
			for (MethodCover methodCover : remainingTestCase.getSummarizedCoverage().values()) {
				remainingTestCase.unMarkMethod(methodCover);
			}
		}
	}
	
	private static int coveredMethodsInSource(Map<String, ClassCover> sourceMethodCoverage) {
		int amountOfCoveredMethods = 0;
		for (ClassCover cc : sourceMethodCoverage.values()) {
			amountOfCoveredMethods += cc.getMethods().values().size();
		}
		return amountOfCoveredMethods;
	}
}
