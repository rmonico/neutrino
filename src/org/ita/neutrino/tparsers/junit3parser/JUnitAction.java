package org.ita.neutrino.tparsers.junit3parser;


public class JUnitAction extends org.ita.neutrino.tparsers.junitgenericparser.JUnitAction {

	JUnitAction() {
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
