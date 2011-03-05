package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.codeparser.Constructor;
import org.ita.testrefactoring.codeparser.ConstructorInvocationExpression;
import org.ita.testrefactoring.codeparser.Expression;

public class ASTConstructorInvocationExpression extends ASTAbstractExpression implements ConstructorInvocationExpression {

	private Constructor constructor;
	private List<Expression> parameterList = new ArrayList<Expression>();
	
	@Override
	public Constructor getCalledConstructor() {
		return constructor;
	}
	
	protected void setCalledConstructor(Constructor constructor) {
		this.constructor = constructor;
	}

	@Override
	public List<Expression> getParameterList() {
		return parameterList;
	}
	
}
