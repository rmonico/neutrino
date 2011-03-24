package org.ita.neutrino.astparser;

/**
 * Indica que o elemento do AST possui propriedades alteráveis.
 * 
 * @author Rafael Monico
 *
 */
interface ASTWritableElement {

	/**
	 * Informa que o parse foi finalizado.
	 */
	public void parseFinished(); 
}
