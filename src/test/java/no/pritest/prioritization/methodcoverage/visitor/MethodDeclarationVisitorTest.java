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
import japa.parser.ast.body.BodyDeclaration;
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
import static org.junit.Assert.assertThat;

public class MethodDeclarationVisitorTest {
	
	private MethodDecl methodDeclaration;

	@Before
	public void setup() throws FileNotFoundException, ParseException {
		FileInputStream fis = new FileInputStream("src/main/java/no/pritest/prioritization/methodcoverage/visitor/MethodDeclarationVisitor.java");
		CompilationUnit cu = JavaParser.parse(fis);
		
		MethodDeclarationVisitor mdv = new MethodDeclarationVisitor();
		BodyDeclaration bd = cu.getTypes().get(0).getMembers().get(0);
		methodDeclaration = bd.accept(mdv, null);
	}
	
	@Test
	public void should_find_declared_method() {
		List<ReferenceType> parameterTypes2 = new ArrayList<ReferenceType>();
		
		parameterTypes2.add(new ReferenceType("MethodDeclaration", "n"));
		parameterTypes2.add(new ReferenceType("Object", "arg1"));
		
		assertThat(methodDeclaration, is(equalTo(new MethodDecl("MethodDecl", "visit", parameterTypes2))));
	}
}
