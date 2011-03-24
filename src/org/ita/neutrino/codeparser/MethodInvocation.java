package org.ita.neutrino.codeparser;

import java.util.List;

public interface MethodInvocation {
	
	Method getCalledMethod();
	
	List<Expression> getParameterList();
}
