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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassType {

	private String name;
    private List<MethodDecl> methodDeclarations;
    private Map<String, MethodDecl> methodDeclMap;
    private Map<String, ReferenceType> fields;
	private List<ClassType> innerClasses;
	private String packageName;
    private String superClass;

    public ClassType(String packageName, String className, String superClass) {
		this.packageName = packageName;
		this.name = className;
        this.superClass = superClass;
        this.fields = new HashMap<String, ReferenceType>();
        this.methodDeclarations = new ArrayList<MethodDecl>();
        this.methodDeclMap = new HashMap<String, MethodDecl>();
        this.innerClasses = new ArrayList<ClassType>();

        fields.put("this", new ReferenceType(this.name, "this"));
	}

	public String getName() {
		return this.name;
	}
    
    public Map<String, MethodDecl> getMethodDeclarations() {
        return methodDeclMap;
    }

    public Map<String, ReferenceType> getFields() {
        return fields;
    }

	public List<ClassType> getInnerClasses() {
		return this.innerClasses;
	}

	public String getPackageName() {
		return packageName;
	}

    public String getSuperClass() {
        return superClass;
    }

    public void putMethodDeclaration(MethodDecl methodDeclaration) {
        this.methodDeclMap.put(MethodDecl.createUniqueKeyForClass(methodDeclaration), methodDeclaration);
    }
}
