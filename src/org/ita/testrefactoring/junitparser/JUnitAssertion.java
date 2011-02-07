package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.ita.testrefactoring.parser.Assertion;
import org.ita.testrefactoring.parser.MethodInvocationExpression;
import org.ita.testrefactoring.parser.MethodParameter;

public class JUnitAssertion extends JUnitTestElement implements Assertion {

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
		System.out.println(getMethodInvocation().typeArguments().getClass());
		System.out.println(getMethodInvocation().typeArguments());
		
		for (Object o : getMethodInvocation().arguments()) {
			ASTNode node = (ASTNode) o;
			
			MethodParameter parameter = new MethodParameter();
			
			parameter.setType("");
			
			if (node instanceof MethodInvocation) {
				
//				MethodInvocation mi = (MethodInvocation) node;
				
				MethodInvocationExpression expression = new MethodInvocationExpression();
				
				expression.setValue(node.toString());
				
				parameter.setExpression(expression);
			} else {
				throw new JUnitInternalParserError("Tipo de nó não suportado por este parser :-(");
			}
		}
		
		
	}
	
}
