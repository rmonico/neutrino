package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocationExpression;

public class ASTMethodInvocationExpression extends ASTAbstractExpression implements MethodInvocationExpression {

	private Method calledMethod;
	private List<Expression> parameterList = new ArrayList<Expression>();

	public ASTMethodInvocationExpression() {
	}
	
	@Override
	public Method getCalledMethod() {
		return calledMethod;
	}
	
	protected void setCalledMethod(Method calledMethod) {
		this.calledMethod = calledMethod;
	}

	@Override
	public List<Expression> getParameterList() {
		return parameterList;
	}

	@Override
	public String toString() {
		return getCalledMethod().getName();
	}

	@Override
	public String getValue() {
		return null;
	}
}
