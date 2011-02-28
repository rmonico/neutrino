package org.ita.testrefactoring.metacode;

import java.util.List;

public interface ConstructorInvocation {

	Constructor getCalledConstructor();
	
	List<Expression> getParameterList();

}
