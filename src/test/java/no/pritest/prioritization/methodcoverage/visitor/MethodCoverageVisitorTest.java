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
import no.pritest.prioritization.methodcoverage.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class MethodCoverageVisitorTest {

    private ClassCover methodDeclarationVisitorClass;

    @Before
    public void setup_MethodDeclarationVisitor_test_object() throws FileNotFoundException, ParseException {
        FileInputStream fis = new FileInputStream("src/main/java/no/pritest/prioritization/methodcoverage/visitor/MethodDeclarationVisitor.java");
		CompilationUnit cu = JavaParser.parse(fis);

        Map<String, ClassType> classesInProject = new HashMap<String, ClassType>();

        ClassType callingClass = new ClassType("no.pritest.prioritization.methodcoverage.visitor", "MethodDeclarationVisitor", null);
        List<ReferenceType> params1 = new ArrayList<ReferenceType>();
        params1.add(new ReferenceType("MethodDeclaration", "n"));
        params1.add(new ReferenceType("Object", "arg1"));
        callingClass.putMethodDeclaration(new MethodDecl("void", "visit", params1));
        List<ReferenceType> extractParameterParams = new ArrayList<ReferenceType>();
        extractParameterParams.add(new ReferenceType("Parameter", "p"));
        callingClass.putMethodDeclaration(new MethodDecl("ReferenceType", "extractParameter", extractParameterParams));

        ClassType firstClass = new ClassType("japa.parser.ast.body", "MethodDeclaration", null);
        firstClass.putMethodDeclaration(new MethodDecl("String", "getName", new ArrayList<ReferenceType>()));
        firstClass.putMethodDeclaration(new MethodDecl("Type", "getType", new ArrayList<ReferenceType>()));
        firstClass.putMethodDeclaration(new MethodDecl("List", "getParameters", new ArrayList<ReferenceType>()));

        ClassType secondClass = new ClassType("japa.parser.ast.body", "Parameter", null);
        List<ReferenceType> params2 = new ArrayList<ReferenceType>();
        params2.add(new ReferenceType("GenericVisitor", "v"));
        params2.add(new ReferenceType("A", "arg"));
        secondClass.putMethodDeclaration(new MethodDecl("R", "accept", params2));
        List<ReferenceType> params3 = new ArrayList<ReferenceType>();
        params3.add(new ReferenceType("VoidVisitor", "v"));
        params3.add(new ReferenceType("A", "arg"));
        secondClass.putMethodDeclaration(new MethodDecl("void", "accept", params3));

        ClassType thirdClass = new ClassType("no.pritest.prioritization.methodcoverage.visitor", "ReturnTypeVisitor", null);
        thirdClass.putMethodDeclaration(new MethodDecl("String", "getTypeName", new ArrayList<ReferenceType>()));

        classesInProject.put("MethodDeclarationVisitor", callingClass);
        classesInProject.put("MethodDeclaration", firstClass);
        classesInProject.put("ReturnTypeVisitor", thirdClass);

        MethodCoverageVisitor mcv = new MethodCoverageVisitor(classesInProject);
        cu.getTypes().get(0).accept(mcv, null);
        Map<String, ClassCover> coveredClasses = mcv.getCoveredClasses();

        methodDeclarationVisitorClass = coveredClasses.get("MethodDeclarationVisitor");
    }

    @Test
    public void should_find_classes_in_compilation_unit() {
        String className = methodDeclarationVisitorClass.getName();
        
        assertThat(className, is(equalTo("MethodDeclarationVisitor")));
    }

    @Test
    public void should_find_methods_declared_within_classes() {
    	List<ReferenceType> params1 = new ArrayList<ReferenceType>();
        params1.add(new ReferenceType("MethodDeclaration", "n"));
    	
        List<ProcessedMethodCall> methodCalls = new ArrayList<ProcessedMethodCall>();
        methodCalls.add(new ProcessedMethodCall("ReturnTypeVisitor", "getTypeName", new ArrayList<String>()));

        assertThat(methodDeclarationVisitorClass.getMethods().values(), hasItems(
                new MethodCover("MethodDeclarationVisitor", "String", "extractMethodReturnType", params1, methodCalls)
        ));
    }

    @Test
    public void should_find_calls_to_private_methods_and_parameters() {
        //  TODO: split into two tests
        List<ReferenceType> params = new ArrayList<ReferenceType>();
        params.add(new ReferenceType("MethodDeclaration", "n"));
        params.add(new ReferenceType("Object", "arg1"));
        
        List<ProcessedMethodCall> methodCalls = new ArrayList<ProcessedMethodCall>();
        methodCalls.add(new ProcessedMethodCall("MethodDeclaration", "getName", new ArrayList<String>()));
        methodCalls.add(new ProcessedMethodCall("MethodDeclaration", "getType", new ArrayList<String>()));
        methodCalls.add(new ProcessedMethodCall("MethodDeclaration", "getParameters", new ArrayList<String>()));
        methodCalls.add(new ProcessedMethodCall("MethodDeclaration", "getParameters", new ArrayList<String>()));

        List<String> extractParameterParams = new ArrayList<String>();
        extractParameterParams.add("Parameter");
        methodCalls.add(new ProcessedMethodCall("MethodDeclarationVisitor", "extractParameter", extractParameterParams));

        assertThat(methodDeclarationVisitorClass.getMethods().values(), hasItems(
                new MethodCover("MethodDeclarationVisitor", "MethodDecl", "visit", params, methodCalls)
        ));
    }

    @Test
    public void should_not_include_methods_not_belonging_to_the_analyzed_project() {
        List<ReferenceType> parameters = new ArrayList<ReferenceType>();
        parameters.add(new ReferenceType("E", "e"));

        assertThat(methodDeclarationVisitorClass.getMethods().values(), not(hasItems(
                new MethodCover("List", "boolean", "add", parameters, new ArrayList<ProcessedMethodCall>())
        )));
    }
    
    @Test
    public void should_include_methods_called_by_field_variables() throws ParseException, FileNotFoundException {
    	FileInputStream fis = new FileInputStream("src/test/java/no/pritest/prioritization/methodcoverage/visitor/MethodCoverageVisitorTest.java");
		CompilationUnit cu = JavaParser.parse(fis);
		
		Map<String, ClassType> classesInProject = new HashMap<String, ClassType>();
		
		ClassType classCoverClass = new ClassType("no.pritest.prioritization.methodcoverage.model", "ClassCover", null);
        classCoverClass.putMethodDeclaration(new MethodDecl("String", "getName", new ArrayList<ReferenceType>()));
		classesInProject.put("ClassCover", classCoverClass);

        ClassType theTestClass = new ClassType("no.pritest.prioritization.methodcoverage.visitor", "MethodCoverageVisitorTest", null);
        theTestClass.putMethodDeclaration(new MethodDecl("void", "should_find_classes_in_compilation_unit", new ArrayList<ReferenceType>()));
        theTestClass.getFields().put("methodDeclarationVisitorClass", new ReferenceType("ClassCover", "methodDeclarationVisitorClass"));
        classesInProject.put("MethodCoverageVisitorTest", theTestClass);

        MethodCoverageVisitor mcv = new MethodCoverageVisitor(classesInProject);
        cu.accept(mcv, null);
        Map<String, ClassCover> coveredClasses = mcv.getCoveredClasses();

        List<ProcessedMethodCall> methodCalls = new ArrayList<ProcessedMethodCall>();
        methodCalls.add(new ProcessedMethodCall("ClassCover", "getName", new ArrayList<String>()));

        assertThat(coveredClasses.get("MethodCoverageVisitorTest").getMethods().values(), hasItems(
                new MethodCover("MethodCoverageVisitorTest", "void", "should_find_classes_in_compilation_unit",
                        new ArrayList<ReferenceType>(), methodCalls)
        ));
    }
    
    @Test
    public void should_support_overloaded_method_declarations() throws FileNotFoundException, ParseException {
    	FileInputStream fis = new FileInputStream("src/main/java/no/pritest/prioritization/methodcoverage/visitor/MethodCoverageVisitor.java");
		CompilationUnit cu = JavaParser.parse(fis);
		
		MethodCoverageVisitor mcv = new MethodCoverageVisitor(new HashMap<String, ClassType>());
        cu.accept(mcv, null);
        ClassCover classCover = mcv.getCoveredClasses().get("MethodCoverageVisitor");
        
        List<ReferenceType> params1 = new ArrayList<ReferenceType>();
        params1.add(new ReferenceType("MethodDeclaration", "n"));
        params1.add(new ReferenceType("ClassCover", "arg"));
        
        List<ReferenceType> params2 = new ArrayList<ReferenceType>();
        params2.add(new ReferenceType("ClassOrInterfaceDeclaration", "n"));
        params2.add(new ReferenceType("ClassCover", "arg"));
        
        List<ReferenceType> params3 = new ArrayList<ReferenceType>();
        params3.add(new ReferenceType("CompilationUnit", "arg0"));
        params3.add(new ReferenceType("ClassCover", "arg1"));
        
		assertThat(classCover.getMethods().values(), hasItems(
        		new MethodCover("MethodCoverageVisitor", "void", "visit", params1, new ArrayList<ProcessedMethodCall>()),
        		new MethodCover("MethodCoverageVisitor", "void", "visit", params2, new ArrayList<ProcessedMethodCall>()),
        		new MethodCover("MethodCoverageVisitor", "void", "visit", params3, new ArrayList<ProcessedMethodCall>())
        ));
    }
}
