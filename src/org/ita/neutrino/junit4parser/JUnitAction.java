package org.ita.neutrino.junit4parser;


public class JUnitAction extends org.ita.neutrino.junitgenericparser.JUnitAction {

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
