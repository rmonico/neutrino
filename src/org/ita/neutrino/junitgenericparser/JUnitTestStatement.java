package org.ita.neutrino.junitgenericparser;

import org.ita.neutrino.abstracttestparser.TestStatement;

public abstract class JUnitTestStatement implements TestStatement {

	private JUnitTestMethod parent;
	
	void setParent(JUnitTestMethod parent) {
		this.parent = parent;
	}

	@Override
	public JUnitTestMethod getParent() {
		return parent;
	}
	
	@Override
	public String toString() {
		return getCodeElement().toString();
	}
}
