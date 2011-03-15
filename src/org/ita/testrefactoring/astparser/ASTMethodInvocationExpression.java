package org.ita.testrefactoring.astparser;

import java.util.List;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocationExpression;

public class ASTMethodInvocationExpression extends ASTAbstractExpression implements MethodInvocationExpression {

	private MethodInvocationHandler mih = new MethodInvocationHandler();

	public ASTMethodInvocationExpression() {
	}
	
	@Override
	public Method getCalledMethod() {
		return mih.getCalledMethod();
	}
	
	protected void setCalledMethod(Method calledMethod) {
		mih.setCalledMethod(calledMethod);
	}

	@Override
	public List<Expression> getParameterList() {
		return mih.getParameterList();
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
