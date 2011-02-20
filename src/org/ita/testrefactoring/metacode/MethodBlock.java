package org.ita.testrefactoring.metacode;

/**
 * Representa o bloco de código principal de um método.
 * 
 * @author Rafael Monico
 *
 */
public interface MethodBlock extends Block {
	Method getParentMethod();
}
