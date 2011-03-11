package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocationStatement;

public class ASTMethodInvocationStatement extends ASTAbstractStatement<org.eclipse.jdt.core.dom.MethodInvocation> implements MethodInvocationStatement {

	private Method method;
	private List<Expression> parameterList = new ArrayList<Expression>();

	@Override
	public Method getCalledMethod() {
		return method;
	}
	
	void setCalledMethod(Method method) {
		this.method = method;
	}

	@Override
	public List<Expression> getParameterList() {
		return parameterList;
	}

}
