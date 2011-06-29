package org.ita.neutrino.junit3parser;

import org.ita.neutrino.junit4parser.JUnitTestMethod;

public class JUnitAssertion extends org.ita.neutrino.junitgenericparser.JUnitAssertion implements JUnitTestStatement {
	
	JUnitAssertion() {
		super();
	}
	
	
	@Override
	public JUnitTestMethod getParent() {
		return (JUnitTestMethod) super.getParent();
	}

	public void setParent(JUnitTestMethod parent) {
		super.setParent(parent);
	}

}
