package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class TypeVisitor extends VoidVisitorAdapter<Object> {

    private String name;

    @Override
    public void visit(ClassOrInterfaceType n, Object arg) {
        this.name = n.getName();
    }

    public String getName() {
        return this.name;
    }
}
