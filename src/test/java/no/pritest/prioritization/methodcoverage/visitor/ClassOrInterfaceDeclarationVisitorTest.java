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
import no.pritest.prioritization.methodcoverage.model.ReferenceType;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.hamcrest.collection.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class ClassOrInterfaceDeclarationVisitorTest {

    private List<ClassType> classes;

	@Before
	public void setup() throws FileNotFoundException, ParseException {
		FileInputStream fis = new FileInputStream("src/main/java/no/pritest/prioritization/methodcoverage/visitor/MethodCallVisitor.java");
		CompilationUnit cu = JavaParser.parse(fis);

        ClassOrInterfaceDeclarationVisitor cidv = new ClassOrInterfaceDeclarationVisitor("");

		cu.accept(cidv, null);

        classes = cidv.getTypes();
	}

	@Test
	public void should_return_class_name() {
        ClassType classType = classes.get(0);

        assertThat(classType.getName(), is(equalTo("MethodCallVisitor")));
	}

    @Test
    public void should_return_correct_amount_of_classes_from_compilation_unit() {
        assertThat(classes.size(), is(equalTo(1)));
        assertThat(classes.get(0).getInnerClasses().size(), is(equalTo(5)));
    }
	
	@Test
    public void should_return_inner_classes() {
        ClassType innerClass = classes.get(0).getInnerClasses().get(0);

        assertThat(innerClass.getName(), is(equalTo("ScopeVisitor")));
    }

    @Test
    public void should_integrate_with_FieldVisitor() {
        ClassType outerClass = classes.get(0);
        ClassType innerClass = classes.get(0).getInnerClasses().get(2);

        assertThat(outerClass.getFields().values(), hasItem(new ReferenceType("List", "methodCalls")));
        
        assertThat(innerClass.getFields().values(), hasItem(new ReferenceType("String", "scope")));
    }

    @Test
	public void should_integrate_with_MethodDeclarationVisitor() {
        ClassType outerClass = classes.get(0);
        ClassType innerClass = classes.get(0).getInnerClasses().get(2);

        List<ReferenceType> params1 = new ArrayList<ReferenceType>();
        List<ReferenceType> params2 = new ArrayList<ReferenceType>();
        List<ReferenceType> params3 = new ArrayList<ReferenceType>();

        params2.add(new ReferenceType("MethodCallExpr", "n"));
        params2.add(new ReferenceType("Object", "arg1"));
        
        assertThat(outerClass.getMethodDeclarations().size(), is(equalTo(5)));
		assertThat(outerClass.getMethodDeclarations().values(), hasItems(
                new MethodDecl("List", "getRawMethodCalls", params1),
                new MethodDecl("void", "visit", params2)
        ));
		
		assertThat(innerClass.getMethodDeclarations().size(), is(equalTo(3)));
		assertThat(innerClass.getMethodDeclarations().values(), hasItems(
                new MethodDecl("String", "getScope", params1),
                new MethodDecl("NestedMethodCall", "getNestedCall", params3)
        ));
	}

    @Test
    public void should_support_extend_statements() {
        ClassType theClass = classes.get(0);

        assertThat(theClass.getSuperClass(), is(equalTo("VoidVisitorAdapter")));
    }
}
