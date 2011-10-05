package org.ita.neutrino.junit4parser;


public class JUnitAssertion extends org.ita.neutrino.junitgenericparser.JUnitAssertion {

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
