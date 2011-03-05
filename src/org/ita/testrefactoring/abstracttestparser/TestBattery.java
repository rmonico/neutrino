package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

public abstract class TestBattery implements TestFragment {

	public abstract List<TestSuite> getTestSuiteList();
	
	public abstract TestSuite createTestSuite();
	
	@Override
	public TestFragment getParent() {
		// A bateria não possui parent
		return null;
	}
	
	public abstract AbstractTestParser getParser();
}
