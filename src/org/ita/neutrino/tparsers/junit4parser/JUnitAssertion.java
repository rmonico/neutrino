package org.ita.neutrino.tparsers.junit4parser;



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


	

}
