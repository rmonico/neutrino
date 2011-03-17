package org.ita.testrefactoring.codeparser;

/**
 * Interface que representa um parsing de código fonte.
 * 
 * @author Rafael Monico
 * 
 */
public interface CodeParser {

	/**
	 * Devolve o objeto de seleção. A função desse objeto é de informar ao
	 * parsing sobre a seleção atual no código fonte. Isto é, a seleção deve ser
	 * informada para a classe que implementa essa interface, e não o contrário.
	 * 
	 * @return
	 */
	CodeSelection getSelection();

	/**
	 * Faz o parsing do código fonte, como o nome sugere.
	 * 
	 * @throws ParserException
	 */
	void parse() throws ParserException;

	/**
	 * Após o parsing, devolverá o objeto contendo a representação abstrata do
	 * código fonte.
	 * 
	 * @return
	 */
	Environment getEnvironment();

}
