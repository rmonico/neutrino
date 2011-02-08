package org.ita.testrefactoring.metacode;

/**
 * Indica que a classe é um elemento de código interno a um bloco.
 * @author Rafael Monico
 *
 */
public interface Statement {
	Block getParent();
}
