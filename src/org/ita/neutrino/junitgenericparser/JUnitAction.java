package org.ita.neutrino.junitgenericparser;

import org.ita.neutrino.abstracttestparser.Action;
import org.ita.neutrino.codeparser.Statement;

public abstract class JUnitAction implements JUnitTestStatement, Action {

	private Statement element;
	private JUnitTestMethod parent;

	protected JUnitAction() {
		
	}

	@Override
	public void setParent(JUnitTestMethod parent) {
		this.parent = parent;
	}

	@Override
	public JUnitTestMethod getParent() {
		return parent;
	}

	@Override
	public Statement getCodeElement() {
		return element;
	}
	
	void setCodeElement(Statement element) {
		this.element = element;
	}

}
