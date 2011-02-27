package org.ita.testrefactoring.metacode;

import java.util.List;

public interface Block {

	public List<? extends Statement> getStatementList();

	/**
	 * Deve devolver o método onde o bloco está, mesmo que o bloco não seja o corpo do método.
	 * 
	 * @return
	 */
	Method getParentMethod();
}
