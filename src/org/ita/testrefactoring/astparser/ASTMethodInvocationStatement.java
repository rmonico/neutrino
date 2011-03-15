package org.ita.testrefactoring.astparser;

import java.util.List;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocationStatement;

public class ASTMethodInvocationStatement extends ASTAbstractStatement<org.eclipse.jdt.core.dom.MethodInvocation> implements MethodInvocationStatement {

	private MethodInvocationHandler mih = new MethodInvocationHandler();

	@Override
	public Method getCalledMethod() {
		return mih.getCalledMethod();
	}
	
	void setCalledMethod(Method method) {
		mih.setCalledMethod(method);
	}

	@Override
	public List<Expression> getParameterList() {
		return mih.getParameterList();
	}

}
