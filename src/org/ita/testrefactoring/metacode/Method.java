package org.ita.testrefactoring.metacode;

import java.util.List;

/**
 * Representa uma declaração de método dentro de uma classe.
 * 
 * @author Rafael Monico
 *
 */
public interface Method extends TypeElement {
	
	List<Annotation> getAnnotations();
	
	MethodDeclarationNonAccessModifier getNonAccessModifier();
	
	Type getReturnType();
	
	List<Argument> getArgumentList();
	
	List<CheckedExceptionClass> getThrownExceptions();

}
