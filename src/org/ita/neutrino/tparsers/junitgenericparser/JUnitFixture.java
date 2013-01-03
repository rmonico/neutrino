package org.ita.neutrino.tparsers.junitgenericparser;

import org.ita.neutrino.codeparser.Field;
import org.ita.neutrino.tparsers.abstracttestparser.Fixture;

public class JUnitFixture implements Fixture {

	private JUnitTestSuite parent;
	private Field element;

	protected JUnitFixture() {
	}
	
	@Override
	public JUnitTestSuite getParent() {
		return parent;
	}
	
	void setParent(JUnitTestSuite parent) {
		this.parent = parent;
	}

	@Override
	public String getName() {
		return element.getName();
	}
	
	@Override
	public Field getCodeElement() {
		return element;
	}
	
	void setCodeElement(Field element) {
		this.element = element;
	}
}
