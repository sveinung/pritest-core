package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class ReturnTypeVisitor extends VoidVisitorAdapter<Object> {

    private String typeName;

    @Override
    public void visit(ClassOrInterfaceType n, Object arg1) {
        this.typeName = n.getName();
    }

    public String getTypeName() {
        return typeName;
    }
}
