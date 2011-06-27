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

package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import no.pritest.prioritization.methodcoverage.model.ClassType;
import no.pritest.prioritization.methodcoverage.model.MethodDecl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class CompilationUnitVisitorTest {

	private static CompilationUnitVisitor cuv;

	@BeforeClass
	public static void setup_class() throws FileNotFoundException, ParseException {
		FileInputStream fis = new FileInputStream("src/main/java/no/pritest/prioritization/methodcoverage/visitor/CompilationUnitVisitor.java");
		CompilationUnit cu = JavaParser.parse(fis);
		
		cuv = new CompilationUnitVisitor();
		cu.accept(cuv, null);
	}
	
	@Test
	public void should_integrate_with_ClassOrInterfaceDeclarationVisitors_class_name() {
		List<ClassType> types = cuv.getTypes();
		
		assertThat(types.get(0).getName(), is(equalTo("CompilationUnitVisitor")));
	}
	
	@Test
	public void should_integrate_with_ClassOrInterfaceDeclarationVisitors_package_name() {
		List<ClassType> types = cuv.getTypes();
		
		assertThat(types.get(0).getPackageName(), is(equalTo("no.pritest.prioritization.methodcoverage.visitor")));
	}
	
	@Test
	public void should_retrieve_import_statements() {
		List<String> imports = cuv.getImportStatements();
		
		assertThat(imports, hasItems(
				"no.pritest.prioritization.methodcoverage.model.ClassType",
				"japa.parser.ast.visitor.VoidVisitorAdapter"));
	}
	
	@Test
	public void should_get_package_name() {
		String packageName = cuv.getPackageName();
		
		assertThat(packageName, is(equalTo("no.pritest.prioritization.methodcoverage.visitor")));
	}
	
	@Test
	public void should_provide_class_types_in_a_map() {
		Map<String, ClassType> types = cuv.getTypesAsMapItems();
		
		ClassType type = types.get("CompilationUnitVisitor");

        List<String> params = new ArrayList<String>();
        params.add("CompilationUnit");
        params.add("Object");

        assertThat(type.getName(), is(equalTo("CompilationUnitVisitor")));
        assertThat(type.getMethodDeclarations().get(MethodDecl.createUniqueKeyForClass("visit", params)).getMethodName(), is(equalTo("visit")));
	}
}
