package org.ita.neutrino.tparsers.junit3parser;

public class JUnitAssertion extends org.ita.neutrino.tparsers.junitgenericparser.JUnitAssertion {

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
	
	@Override
	protected int assertionIndex() {
		return 1;
	}
	
	@Override
	protected int explanationIndex() {
		return 0;
	}
	
}
