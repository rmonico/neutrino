package org.ita.neutrino.codeparser;

import java.util.List;

public interface ConstructorInvocation {

	Constructor getCalledConstructor();
	
	List<Expression> getParameterList();

}
