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

package no.pritest.prioritization.methodcoverage.model;

import java.util.HashMap;
import java.util.Map;

public class SummarizedTestCase implements Comparable<SummarizedTestCase> {

    private ClassCover testCase;
    private Map<String, MethodCover> summarizedCoverage;
    private Map<String, Boolean> covered;

    public SummarizedTestCase(ClassCover testCase, Map<String, MethodCover> summarizedCoverage) {
        this.testCase = testCase;
        this.summarizedCoverage = summarizedCoverage;
        this.covered = new HashMap<String, Boolean>();
        
        for (MethodCover coveredMethod : summarizedCoverage.values()) {
        	this.covered.put(MethodCover.createUniqueMapKey(coveredMethod), true);
        }
    }

    public ClassCover getTestCase() {
        return testCase;
    }

    public Map<String, MethodCover> getSummarizedCoverage() {
        return summarizedCoverage;
    }

    public Map<String, Boolean> getCovered() {
		return covered;
	}

	public int compareTo(SummarizedTestCase summarizedTestCase) {
		Integer sizeOfThis = new Integer(this.coveredMethods());
		return sizeOfThis.compareTo(new Integer(summarizedTestCase.coveredMethods()));
    }
	
	public int coveredMethods() {
		int coveredMethods = 0;
		for (Boolean method : covered.values()) {
			if (method.equals(Boolean.TRUE)) {
				coveredMethods++;
			}
		}
		return coveredMethods;
	}

	public void markMethod(MethodCover coveredMethod) {
		if (covered.get(MethodCover.createUniqueMapKey(coveredMethod)) != null) {
			covered.put(MethodCover.createUniqueMapKey(coveredMethod), false);
		}
	}
	
	public void unMarkMethod(MethodCover coveredMethod) {
		if (covered.get(MethodCover.createUniqueMapKey(coveredMethod)) != null) {
			covered.put(MethodCover.createUniqueMapKey(coveredMethod), true);
		}
	}
}
