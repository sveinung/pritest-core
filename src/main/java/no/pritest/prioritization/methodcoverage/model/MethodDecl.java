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

public class MethodDecl {

	private final String returnType;
	private final String methodName;
	private List<ReferenceType> parameters;

	public MethodDecl(String returnType, String methodName, List<ReferenceType> parameters) {
		this.returnType = returnType;
		this.methodName = methodName;
		this.parameters = parameters;
	}

	public String getReturnType() {
		return returnType;
	}

	public String getMethodName() {
		return methodName;
	}
	
	public List<ReferenceType> getParameters() {
		return this.parameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result
				+ ((returnType == null) ? 0 : returnType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodDecl other = (MethodDecl) obj;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		return true;
	}

    public static String createUniqueKeyForClass(MethodDecl methodDeclaration) {
        String key = methodDeclaration.getMethodName();

        for (ReferenceType parameter : methodDeclaration.getParameters()) {
            key += "." + parameter.getType();
        }

        return key;
    }

    public static String createUniqueKeyForClass(String methodName, List<String> parameters) {
        String key = methodName;

        for (String parameter : parameters) {
            key += "." + parameter;
        }

        return key;
    }
}
