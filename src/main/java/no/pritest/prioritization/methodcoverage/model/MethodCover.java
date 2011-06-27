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

import java.util.List;

public class MethodCover {
    private String className;
    private String returnType;
    private String methodName;
	private List<ReferenceType> parameters;
    private List<ProcessedMethodCall> methodCalls;

    public MethodCover(String className, String returnType, String methodName,
    		List<ReferenceType> params, List<ProcessedMethodCall> methodCalls) {
        this.className = className;
        this.returnType = returnType;
        this.methodName = methodName;
		this.parameters = params;
        this.methodCalls = methodCalls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodCover that = (MethodCover) o;

        if (className != null ? !className.equals(that.className) : that.className != null) return false;
        if (methodCalls != null ? !methodCalls.equals(that.methodCalls) : that.methodCalls != null) return false;
        if (methodName != null ? !methodName.equals(that.methodName) : that.methodName != null) return false;
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;
        if (returnType != null ? !returnType.equals(that.returnType) : that.returnType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = className != null ? className.hashCode() : 0;
        result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
        result = 31 * result + (methodName != null ? methodName.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (methodCalls != null ? methodCalls.hashCode() : 0);
        return result;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getMethodName() {
        return methodName;
    }

	public List<ReferenceType> getParameters() {
		return parameters;
	}

    public String getClassName() {
        return className;
    }

    public List<ProcessedMethodCall> getMethodCalls() {
        return methodCalls;
    }
    
    public static String createUniqueMapKey(final MethodCover methodCover) {
		String key = methodCover.className + "." + methodCover.methodName;
		
		for (ReferenceType parameter : methodCover.parameters) {
			key += (":" + parameter.getType());
		}
		
		return key;
	}

    public static String createUniqueMapKey(final ProcessedMethodCall processedCall) {
        String key = processedCall.getClassName() + "." + processedCall.getMethodName();

		for (String parameter : processedCall.getParameters()) {
			key += (":" + parameter);
		}

		return key;
    }
}
