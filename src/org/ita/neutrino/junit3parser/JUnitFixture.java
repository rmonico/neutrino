package org.ita.neutrino.junit3parser;

import org.ita.neutrino.abstracttestparser.Fixture;
import org.ita.neutrino.codeparser.Field;

public class JUnitFixture implements Fixture {

	private JUnitTestSuite parent;
	private Field element;

	JUnitFixture() {
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
