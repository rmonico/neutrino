package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocation;
import org.zero.utils.AbstractListListener;
import org.zero.utils.ListWrapper;

class MethodInvocationHandler implements MethodInvocation {

	private Method calledMethod;
	private List<Expression> parameterList;
	
	public MethodInvocationHandler() {
		ListWrapper<Expression> wrapper = new ListWrapper<Expression>(new ArrayList<Expression>());
		
		wrapper.addListener(new AbstractListListener<Expression>() {
			@Override
			public void add(int index, Expression element) {
				parameterListItemAdded(index, element);
			}

		});
		
		parameterList = wrapper;
	}
	
	@Override
	public Method getCalledMethod() {
		return calledMethod;
	}

	private void parameterListItemAdded(int index, Expression element) {
//		MethodParameter explanationParameter = new MethodParameter();
//		
//		LiteralExpression explanationStringExpression = new LiteralExpression();
//		
//		explanationStringExpression.setValue(explanationString);
//		
//		explanationParameter.setType("java.lang.String");
//		explanationParameter.setExpression(explanationStringExpression);
//		
//		targetAssertion.getParameters().add(0, explanationParameter);
	}
	
	@Override
	public List<Expression> getParameterList() {
		return parameterList;
	}

	public void setCalledMethod(Method calledMethod) {
		this.calledMethod = calledMethod;
	}

}
