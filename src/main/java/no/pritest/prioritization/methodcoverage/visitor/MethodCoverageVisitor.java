package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import no.pritest.prioritization.methodcoverage.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodCoverageVisitor extends VoidVisitorAdapter<ClassCover> {
    private Map<String, ClassCover> coveredClasses;
    private Map<String, ClassType> classesInProject;
	private String packageName;

    public MethodCoverageVisitor(Map<String, ClassType> classesInProject) {
        coveredClasses = new HashMap<String, ClassCover>();
        this.classesInProject = classesInProject;
    }
    
    @Override
	public void visit(CompilationUnit arg0, ClassCover arg1) {
		if (arg0.getPackage() != null) {
			packageName = arg0.getPackage().getName().toString();
		}
    	
		if (arg0.getTypes() != null) {
			for (TypeDeclaration td : arg0.getTypes()) {
				td.accept(this, arg1);
			}
		}
	}

	@Override
    public void visit(ClassOrInterfaceDeclaration n, ClassCover arg) {
        ClassCover currentClass = new ClassCover(n.getName(), packageName);

        if (n.getMembers() != null) {
            for (BodyDeclaration member : n.getMembers()) {
                member.accept(this, currentClass);
            }
        }

        coveredClasses.put(currentClass.getName(), currentClass);
    }

    @Override
    public void visit(MethodDeclaration n, ClassCover arg) {
        MethodDecl methodDeclaration = n.accept(new MethodDeclarationVisitor(), null);
    	
    	String returnType = methodDeclaration.getReturnType();
        String methodName = methodDeclaration.getMethodName();
        List<ReferenceType> parameters = methodDeclaration.getParameters();
        
        List<ProcessedMethodCall> methodCalls = new ArrayList<ProcessedMethodCall>();

        if (n.getBody() != null) {
        	Map<String, ReferenceType> localVariables = extractLocalVariables(n);

            for (ReferenceType parameter : parameters) {
                localVariables.put(parameter.getVariableName(), parameter);
            }

        	Map<String, ReferenceType> fieldVariables = retrieveFieldVariablesOfCurrentClass(arg);
        	
            MethodCallVisitor mcv = new MethodCallVisitor(localVariables, fieldVariables);
            n.getBody().accept(mcv, null);
            
            for (RawMethodCall rawMethodCall : mcv.getRawMethodCalls()) {
            	ProcessedMethodCall processedMethodCall = processRawMethodCall(localVariables, rawMethodCall, arg);
            	if (processedMethodCall != null) {
            		methodCalls.add(processedMethodCall);
            	}
            }
        }

        MethodCover declaredMethod = new MethodCover(arg.getName(), returnType, methodName, parameters, methodCalls);
        arg.getMethods().put(MethodCover.createUniqueMapKey(declaredMethod),
        		declaredMethod);
    }

	private Map<String, ReferenceType> retrieveFieldVariablesOfCurrentClass(ClassCover arg) {
		ClassType currentClass = classesInProject.get(arg.getName());
		Map<String, ReferenceType> fieldVariables =
			    (currentClass != null ? currentClass.getFields() : new HashMap<String, ReferenceType>());
		return fieldVariables;
	}
    
    private Map<String, ReferenceType> extractLocalVariables(MethodDeclaration n) {
    	VariableDeclarationVisitor vdv = new VariableDeclarationVisitor();
    	n.getBody().accept(vdv, null);
    	
    	Map<String, ReferenceType> localVariables = vdv.getVariables();
    	
    	return localVariables;
    }
    
    private ProcessedMethodCall processRawMethodCall(Map<String, ReferenceType> localVariables, RawMethodCall rawMethodCall, ClassCover belongingClass) {

        if (rawMethodCall.getScope() != null) {
            ReferenceType variable = localVariables.get(rawMethodCall.getScope());
            if (variable != null) {
                if (isClassInProject(variable)) {
                    return new ProcessedMethodCall(variable.getType(), rawMethodCall.getMethodName(), rawMethodCall.getParameters());
                }
            } else {
                ClassType currentClass = classesInProject.get(belongingClass.getName());
                if (currentClass != null) {
                    variable = currentClass.getFields().get(rawMethodCall.getScope());
                    if (variable != null) {
                        return new ProcessedMethodCall(variable.getType(), rawMethodCall.getMethodName(), rawMethodCall.getParameters());
                    }
                }
            }
        } else {
            ClassType referencedClass = this.classesInProject.get(belongingClass.getName());
            if (referencedClass != null) {
                String key = MethodDecl.createUniqueKeyForClass(rawMethodCall.getMethodName(), rawMethodCall.getParameters());
                if(referencedClass.getMethodDeclarations().get(key) != null) {
                    return new ProcessedMethodCall(belongingClass.getName(), rawMethodCall.getMethodName(), rawMethodCall.getParameters());
                }
            }
        }
        
        return null;
    }

    private boolean isClassInProject(ReferenceType variable) {
        return classesInProject.get(variable.getType()) != null;
    }

    public Map<String,ClassCover> getCoveredClasses() {
        return coveredClasses;
    }
}
