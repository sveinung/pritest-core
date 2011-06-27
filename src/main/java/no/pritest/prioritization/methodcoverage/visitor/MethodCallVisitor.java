package no.pritest.prioritization.methodcoverage.visitor;

import japa.parser.ast.expr.*;
import japa.parser.ast.visitor.GenericVisitorAdapter;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import no.pritest.prioritization.methodcoverage.model.RawMethodCall;
import no.pritest.prioritization.methodcoverage.model.ReferenceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodCallVisitor extends VoidVisitorAdapter<Object> {

	private List<RawMethodCall> methodCalls;
	private final Map<String, ReferenceType> localVariables;
	private final Map<String, ReferenceType> fieldVariables;
	
	public MethodCallVisitor(Map<String, ReferenceType> localVariables, Map<String, ReferenceType> fieldVariables) {
		this.localVariables = localVariables;
		this.fieldVariables = fieldVariables;
		this.methodCalls = new ArrayList<RawMethodCall>();
	}
	
    @Override
	public void visit(MethodCallExpr n, Object arg1) {
		String methodName = n.getName();
        String scope = null;
        List<String> parameters;
        
        scope = retrieveScope(n);
        
        parameters = retrieveArguments(n);
        
        RawMethodCall methodCall = new RawMethodCall(scope, methodName, parameters);
        methodCalls.add(methodCall);
	}
    
    private String retrieveScope(MethodCallExpr n) {
		String scope = null;
		if (n.getScope() != null) {
            scope = n.getScope().accept(new ScopeVisitor(), null);
        }
		return scope;
	}

	private List<String> retrieveArguments(MethodCallExpr n) {
		List<String> parameters = new ArrayList<String>();
		
		if (n.getArgs() != null) {
        	for (Expression expr : n.getArgs()) {
        		ArgumentReference argument = expr.accept(new ArgumentVisitor(), null);
        		
        		addTypeNameToParameterList(parameters, argument);
        	}
        }
		
		return parameters;
	}
	
	private void addTypeNameToParameterList(List<String> parameters, ArgumentReference argument) {
		if (argument != null) {
			if (argument.getType() != null) {
				parameters.add(argument.getType());
			
			} else if (argument.getVariableName() != null) {
				ReferenceType variable = localVariables.get(argument.getVariableName());
				if (variable != null) {
					parameters.add(variable.getType());
				} else {
					variable = fieldVariables.get(argument.getVariableName());
					if (variable != null) {
						parameters.add(variable.getType());
					}
				}
			}
		}
	}
    
	public List<RawMethodCall> getRawMethodCalls() {
		return methodCalls;
	}
	
    private class ScopeVisitor extends GenericVisitorAdapter<String, Object> {

        @Override
        public String visit(NameExpr n, Object arg) {
            if (n.getName() != null) {
                return n.getName();
            } else {
                return null;
            }
        }
    }
    
    private class ScopedMethodCallVisitor extends GenericVisitorAdapter<NestedMethodCall, Object> {
    	
    	@Override
		public NestedMethodCall visit(MethodCallExpr n, Object arg1) {
			//System.out.println("  ScopedMethodCallVisitor.MethodCallExpr: " + n.getScope().toString() + " " + n.getName());
			
			String methodName = n.getName();
			String scope = null;
			NestedMethodCall nestedCall = null;
			
			if (n.getScope() != null) {
				scope = n.getScope().accept(new ScopeVisitor(), null);
				nestedCall = n.getScope().accept(new ScopedMethodCallVisitor(), null);
			}
			
			return new NestedMethodCall(scope, methodName, nestedCall);
		}
    }
    
    private class NestedMethodCall {

		private final String scope;
		private final String methodName;
		private final NestedMethodCall nestedCall;

		public NestedMethodCall(String scope, String methodName, NestedMethodCall nestedCall) {
			this.scope = scope;
			this.methodName = methodName;
			this.nestedCall = nestedCall;
		}

		public String getScope() {
			return scope;
		}

		public String getMethodName() {
			return methodName;
		}

		public NestedMethodCall getNestedCall() {
			return nestedCall;
		}
    }
    
    private class ArgumentVisitor extends GenericVisitorAdapter<ArgumentReference, Object> {

		@Override
		public ArgumentReference visit(NullLiteralExpr n, Object arg) {
			return new ArgumentReference(true);
		}

		@Override
		public ArgumentReference visit(ObjectCreationExpr arg0, Object arg1) {
			return new ArgumentReference(arg0.getType().getName(), null);
		}

		@Override
		public ArgumentReference visit(NameExpr n, Object arg) {
			return new ArgumentReference(null, n.getName());
		}

        @Override
        public ArgumentReference visit(ThisExpr n, Object arg) {
            return new ArgumentReference(null, "this");
        }
    }
    
    private class ArgumentReference extends ReferenceType {

		private final boolean isNull;

		public ArgumentReference(String type, String variableName) {
			super(type, variableName);
			this.isNull = false;
		}

		public ArgumentReference(boolean isNull) {
			super(null, null);
			this.isNull = isNull;
		}

		public boolean isNull() {
			return isNull;
		}
	}
}
