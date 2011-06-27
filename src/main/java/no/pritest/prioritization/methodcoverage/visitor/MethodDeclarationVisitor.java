package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.visitor.GenericVisitorAdapter;
import no.pritest.prioritization.methodcoverage.model.MethodDecl;
import no.pritest.prioritization.methodcoverage.model.ReferenceType;

import java.util.ArrayList;
import java.util.List;


public class MethodDeclarationVisitor extends GenericVisitorAdapter<MethodDecl, Object> {
	
	@Override
	public MethodDecl visit(MethodDeclaration n, Object arg1) {
		String returnType = null;
		String methodName = n.getName();
		
		if (n.getType() != null) {
            returnType = extractMethodReturnType(n);
		}
		
		List<ReferenceType> parameters = new ArrayList<ReferenceType>();
		
		if (n.getParameters() != null) {
			for (Parameter p : n.getParameters()) {
                ReferenceType referenceType = extractParameter(p);
                parameters.add(referenceType);
			}
		}
		
		return new MethodDecl(returnType, methodName, parameters);
	}

    private ReferenceType extractParameter(Parameter p) {
        ParameterVisitor pv = new ParameterVisitor();
        p.accept(pv, null);
        return new ReferenceType(pv.getParameterType(), pv.getParameterVariable());
    }

    private String extractMethodReturnType(MethodDeclaration n) {
        String returnType;
        ReturnTypeVisitor rtv = new ReturnTypeVisitor();
        n.getType().accept(rtv, null);
        returnType = rtv.getTypeName();

        if (returnType == null) {
            returnType = "void";
        }
        return returnType;
    }

}
