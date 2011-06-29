package org.ita.neutrino.junit3parser;

import org.ita.neutrino.abstracttestparser.Action;

public class JUnitAction extends org.ita.neutrino.junitgenericparser.JUnitAction implements JUnitTestStatement, Action {

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
