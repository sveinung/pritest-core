package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import no.pritest.prioritization.methodcoverage.model.ClassType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompilationUnitVisitor extends VoidVisitorAdapter<Object> {

	private List<ClassType> types;
	private List<String> importStatements;
	private String packageName;
	
	public CompilationUnitVisitor() {
		this.types = new ArrayList<ClassType>();
		this.importStatements = new ArrayList<String>();
		this.packageName = null;
	}
	
	@Override
	public void visit(CompilationUnit cu, Object obj) {
		if (cu.getPackage() != null) {
			this.packageName = cu.getPackage().getName().toString();
		}
		
		if (cu.getImports() != null) {
			for (ImportDeclaration id : cu.getImports()) {
				ImportVisitor iv = new ImportVisitor();
				id.accept(iv, null);
				this.importStatements.add(iv.getImportStatement());
			}
		}
		
		if (cu.getTypes() != null) {
			for (TypeDeclaration td : cu.getTypes()) {
				ClassOrInterfaceDeclarationVisitor cidv = new ClassOrInterfaceDeclarationVisitor(this.packageName);
				td.accept(cidv, null);
				this.types.addAll(cidv.getTypes());
			}
		}
	}
	
	public List<ClassType> getTypes() {
		return types;
	}
	
	public List<String> getImportStatements() {
		return this.importStatements;
	}
	
	public String getPackageName() {
		return this.packageName;
	}
	
	public Map<String, ClassType> getTypesAsMapItems() {
		Map<String, ClassType> types = new HashMap<String, ClassType>();
		
		for (ClassType type : this.types) {
			types.put(type.getName(), type);
		}
		
		return types;
	}
}
