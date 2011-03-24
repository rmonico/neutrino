package org.ita.neutrino.codeparser;

/**
 * Marca a classe como um item interno a um tipo.
 * 
 * @author Rafael Monico
 *
 */
public interface TypeElement extends CodeElement {
	
	InnerElementAccessModifier getAccessModifier();
	
	String getName();
	
	/**
	 * Container do element.
	 * @return
	 */
	Type getParent();
}
