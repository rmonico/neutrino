package org.ita.testrefactoring.codeparser;

import java.util.List;

public interface Invokable extends CodeElement {
	
	List<Annotation> getAnnotations();
	
	String getName();
	
	List<Argument> getArgumentList();
	
	List<CheckedExceptionClass> getThrownExceptions();

	Block getBody();
	
	Type getParent();

}
