package org.ita.testrefactoring.codeparser;

import java.util.List;

public interface MethodInvocation {
	
	Method getCalledMethod();
	
	List<Expression> getParameterList();
}
