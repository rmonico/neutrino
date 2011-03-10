package org.ita.testrefactoring.junitparser;

import org.ita.testrefactoring.abstracttestparser.Action;
import org.ita.testrefactoring.codeparser.Statement;

public class JUnitAction extends JUnitTestStatement implements Action {

	private Statement element;

	JUnitAction() {
		
	}

	@Override
	public Statement getCodeElement() {
		return element;
	}
	
	void setCodeElement(Statement element) {
		this.element = element;
	}
}
