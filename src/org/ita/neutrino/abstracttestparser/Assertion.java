package org.ita.neutrino.abstracttestparser;

import org.ita.neutrino.codeparser.MethodInvocationStatement;

public interface Assertion extends TestStatement {

	@Override
	public MethodInvocationStatement getCodeElement();

	/**
	 * Devolve o índice do parâmetro para ser usado com a função
	 * getCodeElement().getParameterList(), caso seja -1 a asserção não possui
	 * explicação.
	 * 
	 * @return
	 */
	public int getExplanationIndex();
}
