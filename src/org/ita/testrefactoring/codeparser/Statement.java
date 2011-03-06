package org.ita.testrefactoring.codeparser;

/**
 * Indica que a classe é um elemento de código interno a um bloco.
 * @author Rafael Monico
 *
 */
public interface Statement extends CodeElement {
	
	Block getParent();
}
