package org.ita.testrefactoring.junitparser;

import org.ita.testrefactoring.abstracttestparser.Fixture;
import org.ita.testrefactoring.abstracttestparser.TestElement;

public class JUnitFixture implements Fixture {

	private TestElement parent;
	private String name;

	JUnitFixture() {
	}
	
	@Override
	public TestElement getParent() {
		return parent;
	}
	
	void setParent(TestElement parent) {
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
