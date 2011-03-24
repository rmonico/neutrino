package org.ita.neutrino.codeparser;


/**
 * Representa uma declaração de método dentro de uma classe.
 * 
 * @author Rafael Monico
 *
 */
public interface Method extends TypeElement, Invokable {
	
	MethodDeclarationNonAccessModifier getNonAccessModifier();
	
	Type getReturnType();
	
}
