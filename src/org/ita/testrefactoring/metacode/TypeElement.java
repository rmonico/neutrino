package org.ita.testrefactoring.metacode;

/**
 * Marca a classe como um item interno a um tipo.
 * 
 * @author Rafael Monico
 *
 */
public interface TypeElement {
	
	InnerElementAccessModifier getAccessModifier();
	
	String getName();
	
	Type getParent();
	
	Type getType();
}
