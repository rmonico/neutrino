package org.ita.testrefactoring.codeparser;

import java.util.List;

public interface Block extends CodeElement {

	public List<? extends Statement> getStatementList();

	/**
	 * Deve devolver o método onde o bloco está, mesmo que o bloco não seja o corpo do método.
	 * 
	 * @return
	 */
	Method getParent();
}
