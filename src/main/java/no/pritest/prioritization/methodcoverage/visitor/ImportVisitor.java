/**
 * 
 */
package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class ImportVisitor extends VoidVisitorAdapter<String> {

	private String importStatement;
	
	public ImportVisitor() {
		this.importStatement = null;
	}

	@Override
	public void visit(ImportDeclaration n, String arg) {
		n.getName().accept(this, arg);
	}

	@Override
	public void visit(NameExpr n, String arg) {
		this.importStatement = n.getName() + "." + this.importStatement;
	}

	@Override
	public void visit(QualifiedNameExpr n, String arg) {
		if (this.importStatement == null) {
			this.importStatement = n.getName();
		} else {
			this.importStatement = n.getName() + "." + this.importStatement;
		}
		
		if (n.getQualifier() != null) {
			n.getQualifier().accept(this, arg);
		}
	}

	public String getImportStatement() {
		return importStatement;
	}
}