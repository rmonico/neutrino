package org.ita.testrefactoring.metacode;

import java.util.List;

public interface Invokable {
	
	List<Annotation> getAnnotations();
	
	String getName();
	
	List<Argument> getArgumentList();
	
	List<CheckedExceptionClass> getThrownExceptions();

	Block getBody();

}
