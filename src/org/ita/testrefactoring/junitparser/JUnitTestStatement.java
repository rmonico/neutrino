package org.ita.testrefactoring.junitparser;

import org.ita.testrefactoring.abstracttestparser.TestStatement;

public abstract class JUnitTestStatement implements TestStatement {

	private JUnitTestMethod parent;
	
	void setParent(JUnitTestMethod parent) {
		this.parent = parent;
	}

	@Override
	public JUnitTestMethod getParent() {
		return parent;
	}
}
