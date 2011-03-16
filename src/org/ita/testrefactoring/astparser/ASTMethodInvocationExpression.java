package org.ita.testrefactoring.astparser;

import java.util.List;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocationExpression;

public class ASTMethodInvocationExpression extends ASTAbstractExpression implements MethodInvocationExpression {

	private MethodInvocationHandler mih = new MethodInvocationHandler(this);

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
	
	@Override
	public void setASTObject(org.eclipse.jdt.core.dom.Expression astObject) {
		assert astObject instanceof org.eclipse.jdt.core.dom.MethodInvocation;
		
		mih.setASTObject((org.eclipse.jdt.core.dom.MethodInvocation) astObject);
		
		super.setASTObject(astObject);
	}
}
