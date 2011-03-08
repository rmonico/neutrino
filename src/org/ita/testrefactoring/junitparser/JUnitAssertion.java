package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.ita.testrefactoring.abstracttestparser.Assertion;
import org.ita.testrefactoring.abstracttestparser.MethodParameter;

public class JUnitAssertion extends JUnitTestStatement implements Assertion {

	private List<MethodParameter> parameterList;
	
	JUnitAssertion() {
		
	}

	public List<MethodParameter> getParameters() {
		
		if (parameterList == null) {
			parameterList = new ArrayList<MethodParameter>();
			
			loadParameterList(parameterList);
		}
			
		return parameterList;
	}

	private void loadParameterList(List<MethodParameter> parameterList) {
//		System.out.println(getMethodInvocation().arguments().getClass());
//		System.out.println(getMethodInvocation().arguments());
//		System.out.println(getMethodInvocation().typeArguments().getClass());
//		System.out.println(getMethodInvocation().typeArguments());
		
		for (Object o : getMethodInvocation().arguments()) {
			ASTNode node = (ASTNode) o;
			
			MethodParameter parameter = new MethodParameter();
			
			parameter.setType("");
			
			if (node instanceof MethodInvocation) {
				
//				MethodInvocationExpression mi = (MethodInvocationExpression) node;
//				
//				MethodInvocationExpression expression = new MethodInvocationExpression();
//				
//				expression.setValue(node.toString());
//				
//				parameter.setExpression(expression);
			} else {
				throw new JUnitInternalParserError("Tipo de nó não suportado por este parser :-(");
			}
		}
		
		
	}

	@Override
	public boolean isExplained() {
		return false;
	}
	
}
