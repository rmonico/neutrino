package org.ita.testrefactoring.junitparser;

import org.ita.testrefactoring.abstracttestparser.Fixture;
import org.ita.testrefactoring.codeparser.Field;

public class JUnitFixture implements Fixture {

	private JUnitTestSuite parent;
	private String name;
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
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}

	@Override
	public Field getCodeElement() {
		return element;
	}
	
	void setCodeElement(Field element) {
		this.element = element;
	}
}
