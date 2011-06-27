package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import no.pritest.prioritization.methodcoverage.model.ReferenceType;

import java.util.HashMap;
import java.util.Map;

public class VariableDeclarationVisitor extends VoidVisitorAdapter<Object> {

	private Map<String, ReferenceType> variables;
	
	public VariableDeclarationVisitor() {
		this.variables = new HashMap<String, ReferenceType>();
	}

	@Override
	public void visit(VariableDeclarationExpr arg0, Object arg1) {
		TypeVisitor tv = new TypeVisitor();
		arg0.getType().accept(tv, null);
		String type = tv.getName();
		
		if (arg0.getVars() != null) {
			for (VariableDeclarator vd : arg0.getVars()) {
                String name = vd.accept(new VariableVisitor(), null);
                variables.put(name, new ReferenceType(type, name));
            }
		}
		
		super.visit(arg0, arg1);
	}
	
	public Map<String, ReferenceType> getVariables() {
		return this.variables;
	}
}
