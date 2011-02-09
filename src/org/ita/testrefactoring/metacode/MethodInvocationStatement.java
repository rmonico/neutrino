package org.ita.testrefactoring.metacode;

import java.util.ArrayList;
import java.util.List;

public class MethodInvocationStatement extends AbstractStatement implements
		MethodInvocation {

	private List<Expression> parameterList = new ArrayList<Expression>();

	@Override
	public List<Expression> getParameterList() {
		return parameterList;
	}

}
