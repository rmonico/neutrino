package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocation;

class MethodInvocationHandler implements MethodInvocation {

	private Method calledMethod;
	private List<Expression> parameterList = new ArrayList<Expression>();
	
	@Override
	public Method getCalledMethod() {
		return calledMethod;
	}

	@Override
	public List<Expression> getParameterList() {
		return parameterList;
	}

	public void setCalledMethod(Method calledMethod) {
		this.calledMethod = calledMethod;
	}

}
