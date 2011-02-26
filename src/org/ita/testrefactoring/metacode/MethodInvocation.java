package org.ita.testrefactoring.metacode;

import java.util.List;

public interface MethodInvocation {
	
	Method getCalledMethod();
	
	List<Expression> getParameterList();
}
