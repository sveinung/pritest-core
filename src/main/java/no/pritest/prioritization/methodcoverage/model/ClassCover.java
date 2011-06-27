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

public class ClassCover {

    private String name;
    private Map<String, MethodCover> methods;
	private final String packageName;

    public ClassCover(String className, String packageName) {
        this.name = className;
		this.packageName = packageName;
        methods = new HashMap<String, MethodCover>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassCover that = (ClassCover) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (methods != null ? !methods.equals(that.methods) : that.methods != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (methods != null ? methods.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public Map<String, MethodCover> getMethods() {
        return methods;
    }

	public String getPackageName() {
		return packageName;
	}
}
