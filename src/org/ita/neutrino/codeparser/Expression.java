package org.ita.neutrino.codeparser;

/**
 * Representa um trecho de código que devolve um valor qualquer.
 * 
 * @author Rafael Monico
 *
 */
public interface Expression {
	
	/**
	 * Indica o type resultante da expressão.
	 * 
	 * @return
	 */
	Type getType();
	
	/**
	 * Devolve o valor da expressão em código, por exemplo: methodInvocationExpression()
	 * 
	 * @return
	 */
	String getValue();
}
