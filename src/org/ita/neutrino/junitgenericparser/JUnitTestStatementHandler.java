package org.ita.neutrino.junitgenericparser;

import org.ita.neutrino.abstracttestparser.TestElement;
import org.ita.neutrino.codeparser.CodeElement;

public class JUnitTestStatementHandler {

	private JUnitTestStatement handled;

	public JUnitTestStatementHandler(JUnitTestStatement handled) {
		this.handled = handled;
	}
	
	@Override
	public boolean equals(Object obj) {
		// Delega a comparação ao elemento do código fonte
		if (obj instanceof TestElement<?>) {
			@SuppressWarnings("unchecked")
			TestElement<CodeElement> testingElement = (TestElement<CodeElement>) obj;
			
			return handled.getCodeElement().equals(testingElement.getCodeElement());
		} else {
			return false;
		}
	}

}
