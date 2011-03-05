package org.ita.testrefactoring.codeparser;

/**
 * Marca a classe como um item interno a um tipo.
 * 
 * @author Rafael Monico
 *
 */
public interface TypeElement {
	
	InnerElementAccessModifier getAccessModifier();
	
	String getName();
	
	/**
	 * Container do element.
	 * @return
	 */
	Type getParentType();
}
