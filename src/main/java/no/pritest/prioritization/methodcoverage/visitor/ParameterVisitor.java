package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.body.Parameter;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

/**
* Created by IntelliJ IDEA.
* User: sveinung
* Date: 4/5/11
* Time: 7:41 PM
* To change this template use File | Settings | File Templates.
*/
public class ParameterVisitor extends VoidVisitorAdapter<Object> {

    private String parameterType = null;
	private String parameterVariable;

    @Override
    public void visit(Parameter parameter, Object obj) {
        if (parameter.getType() != null) {
            parameter.getType().accept(this, obj);
        }
        parameterVariable = parameter.getId().getName();
    }

    @Override
    public void visit(ClassOrInterfaceType cit, Object obj) {
        parameterType = cit.getName();
    }

    public String getParameterType() {
        return parameterType;
    }

	public String getParameterVariable() {
		return parameterVariable;
	}
}
