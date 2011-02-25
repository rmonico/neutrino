package org.ita.testrefactoring.metacode;

import java.util.List;

public interface Block {

	public List<? extends Statement> getStatementList();

	/**
	 * Deve devolver o método onde o bloco está (mesmo que o bloco não esteja
	 * dentro de outro statement que esteja dentro do método.
	 * 
	 * @return
	 */
	ConcreteMethod getMethod();
}
