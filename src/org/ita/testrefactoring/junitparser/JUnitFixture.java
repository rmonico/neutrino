package org.ita.testrefactoring.junitparser;

import org.ita.testrefactoring.abstracttestparser.Fixture;

public class JUnitFixture implements Fixture {

	private JUnitTestSuite parent;
	private String name;

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

}
