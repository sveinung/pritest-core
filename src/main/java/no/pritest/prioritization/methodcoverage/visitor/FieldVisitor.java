package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import no.pritest.prioritization.methodcoverage.model.ReferenceType;

import java.util.HashMap;
import java.util.Map;

public class FieldVisitor extends VoidVisitorAdapter<Object> {
    private Map<String, ReferenceType> fields;

    public FieldVisitor() {
        this.fields = new HashMap<String, ReferenceType>();
    }

    public Map<String, ReferenceType> getFields() {
        return fields;
    }

    @Override
    public void visit(FieldDeclaration n, Object arg) {
        if (n.getType() != null) {
            TypeVisitor tv = new TypeVisitor();
            n.getType().accept(tv, null);
            String type = tv.getName();

            if (n.getVariables() != null) {
                for (VariableDeclarator vd : n.getVariables()) {
                    String name = vd.accept(new VariableVisitor(), null);

                    //this.fields.add(new ReferenceType(type, name));
                    this.fields.put(name, new ReferenceType(type, name));
                }
            }
        }
    }
}
