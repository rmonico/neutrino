package org.ita.neutrino.astparser;


public class GenericExpression extends ASTAbstractExpression<org.eclipse.jdt.core.dom.Expression> {

	/**
	 * Construtor restrito ao pacote.
	 */
	GenericExpression() {
	}

	@Override
	public String getValue() {
		// Continuar aqui, precisa fazer isso devolver um valor não nulo para que a explicação funcione
		return null;
	}
}
