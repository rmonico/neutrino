package org.ita.neutrino.codeparser;

/**
 * Classe que representa uma linha de código.
 * 
 * @author Rafael Monico
 *
 */
public interface Statement extends CodeElement {
	
	@Override
	public Block getParent();
}
