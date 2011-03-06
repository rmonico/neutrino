package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

public abstract class TestBattery implements TestElement {

	public abstract List<TestSuite> getTestSuiteList();
	
	@Override
	public TestElement getParent() {
		// A bateria não possui parent
		return null;
	}
}
