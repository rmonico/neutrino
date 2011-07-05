package org.ita.neutrino.abstracttestparser;

import org.ita.neutrino.codeparser.CodeElement;

public interface TestElement<E extends CodeElement> {
	
	public TestElement<?> getParent();

	E getCodeElement();
}
