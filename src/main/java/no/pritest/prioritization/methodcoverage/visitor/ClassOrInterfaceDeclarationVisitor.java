package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.GenericVisitorAdapter;
import no.pritest.prioritization.methodcoverage.model.ClassType;
import no.pritest.prioritization.methodcoverage.model.MethodDecl;

import java.util.ArrayList;
import java.util.List;

public class ClassOrInterfaceDeclarationVisitor extends GenericVisitorAdapter<ClassType, ClassType> {

    private List<ClassType> types;
	private final String packageName;

    public ClassOrInterfaceDeclarationVisitor(String packageName) {
        this.packageName = packageName;
		this.types = new ArrayList<ClassType>();
    }

    @Override
	public ClassType visit(ClassOrInterfaceDeclaration cid, ClassType classType) {
		String className = cid.getName();
        String superClassName = null;

        if (cid.getExtends() != null) {
            if (cid.getExtends().size() == 1) {
                superClassName = cid.getExtends().get(0).getName();
            }
        }

        ClassType newClass = new ClassType(this.packageName, className, superClassName);

        ClassOrInterfaceDeclarationVisitor cidVisitor =
        	new ClassOrInterfaceDeclarationVisitor(this.packageName + "." + newClass.getName());

        if (cid.getMembers() != null) {
            for (BodyDeclaration bd : cid.getMembers()) {
                bd.accept(cidVisitor, newClass);
            }
        }

        types.add(newClass);

        newClass.getInnerClasses().addAll(cidVisitor.getTypes());
        
        return classType;
	}

    @Override
	public ClassType visit(FieldDeclaration fieldDeclaration, ClassType classType) {
    	FieldVisitor fv = new FieldVisitor();
    	fieldDeclaration.accept(fv, classType);
    	
    	classType.getFields().putAll(fv.getFields());
    	
		return classType;
	}

	@Override
	public ClassType visit(MethodDeclaration methodDeclaration, ClassType classType) {
		MethodDecl methodDecl = methodDeclaration.accept(new MethodDeclarationVisitor(), classType);
		
        classType.putMethodDeclaration(methodDecl);
		
		return classType;
	}

	public List<ClassType> getTypes() {
        return types;
    }
}
