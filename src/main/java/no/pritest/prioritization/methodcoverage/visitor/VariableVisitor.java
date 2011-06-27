package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.visitor.GenericVisitorAdapter;

public class VariableVisitor extends GenericVisitorAdapter<String, Object> {

    @Override
    public String visit(VariableDeclarator n, Object arg) {
        if (n.getId() != null) {
            return n.getId().accept(this, null);
        } else {
            return null;
        }
    }

    @Override
    public String visit(VariableDeclaratorId n, Object arg) {
        return n.getName();
    }
}
