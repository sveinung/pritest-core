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

import no.pritest.prioritization.methodcoverage.model.ClassCover;
import no.pritest.prioritization.methodcoverage.model.MethodCover;
import no.pritest.prioritization.methodcoverage.model.ProcessedMethodCall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodCoverageSummarizer {

	private final Map<String, ClassCover> coveredClasses;
	private Map<String, MethodCover> transitiveMethodCalls;

	public MethodCoverageSummarizer(Map<String, ClassCover> coveredClasses, ClassCover coveredTestCase) {
		this.coveredClasses = coveredClasses;
		
		transitiveMethodCalls = summarizeCoverageOfTestCase(coveredTestCase);
	}

	private Map<String, MethodCover> summarizeCoverageOfTestCase(ClassCover coveredTestCase) {
		Map<String, MethodCover> methods = coveredTestCase.getMethods();
		
		Map<String, MethodCover> transitiveMethodCalls = new HashMap<String, MethodCover>();
		
		for (MethodCover methodCover : methods.values()) {
			List<ProcessedMethodCall> methodCalls = methodCover.getMethodCalls();
			for (ProcessedMethodCall processedCall : methodCalls) {
				ClassCover calledClass = coveredClasses.get(processedCall.getClassName());
				if (calledClass != null) {
					MethodCover calledMethod = calledClass.getMethods().get(MethodCover.createUniqueMapKey(processedCall));
					if (calledMethod != null) {
						transitiveMethodCalls = summarizeRecursively(calledMethod, transitiveMethodCalls);
					}
				}
			}
		}
		
		return transitiveMethodCalls;
	}

	private Map<String, MethodCover> summarizeRecursively(MethodCover methodCall,
			Map<String, MethodCover> transitiveMethodCalls) {


        String methodCallKey = MethodCover.createUniqueMapKey(methodCall);
        if (transitiveMethodCalls.get(methodCallKey) == null) {
			
			transitiveMethodCalls.put(methodCallKey, methodCall);
			
			List<ProcessedMethodCall> methodCalls = methodCall.getMethodCalls();
			for (ProcessedMethodCall processedCall : methodCalls) {
				ClassCover calledClass = coveredClasses.get(processedCall.getClassName());
				if (calledClass != null) {
					MethodCover calledMethod = calledClass.getMethods().get(MethodCover.createUniqueMapKey(processedCall));
					if (calledMethod != null) {
						transitiveMethodCalls = summarizeRecursively(calledMethod, transitiveMethodCalls);
					}
				}
			}
		}
		
		return transitiveMethodCalls;
	}

	public Map<String, MethodCover> getSummarizedCoverage() {
		return transitiveMethodCalls;
	}
}
