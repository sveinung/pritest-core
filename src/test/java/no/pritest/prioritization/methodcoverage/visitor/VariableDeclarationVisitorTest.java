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
import no.pritest.prioritization.methodcoverage.model.ReferenceType;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class VariableDeclarationVisitorTest {
	
	@Test
	public void should_find_variable_declarations_in_method_body() throws FileNotFoundException, ParseException {
		FileInputStream fis = new FileInputStream("src/main/java/no/pritest/prioritization/methodcoverage/model/ReferenceType.java");
		CompilationUnit cu = JavaParser.parse(fis);
		
		VariableDeclarationVisitor vdv = new VariableDeclarationVisitor();
		cu.getTypes().get(0).getMembers().get(6).accept(vdv, null);
		Map<String, ReferenceType> variables = vdv.getVariables();
		
		assertThat(variables.values(), hasItems(new ReferenceType("ReferenceType", "other")));
	}
}
