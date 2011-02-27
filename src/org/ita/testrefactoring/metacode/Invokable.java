package org.ita.testrefactoring.metacode;

import java.util.List;

public interface Invokable {
	
	List<Annotation> getAnnotations();
	
	List<Argument> getArgumentList();
	
	List<CheckedExceptionClass> getThrownExceptions();

	Block getBody();

}
