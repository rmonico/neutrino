package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;


/**
 * Representação de uma expressão onde o parâmetro é uma invocação de método.
 * 
 * @author Rafael Monico
 * 
 */
public class MethodInvocationExpression implements Expression, MethodInvocation {

	private Method calledMethod;
	private List<Expression> parameterList = new ArrayList<Expression>();

	public Method getCalledMethod() {
		return calledMethod;
	}

	void setCalledMethod(Method calledMethod) {
		this.calledMethod = calledMethod;
	}
	
	@Override
	public List<Expression> getParameterList() {
		return parameterList;
	}
}
