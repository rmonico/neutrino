package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.metacode.Expression;
import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.MethodInvocationExpression;

public class ASTMethodInvocationExpression extends AbstractASTExpression implements MethodInvocationExpression {

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
}
