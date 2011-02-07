package org.ita.testrefactoring.parser;

import java.util.List;

public abstract class TestBattery implements TestFragment {

	public abstract List<? extends TestSuite> getTestSuiteList();
	
	public abstract TestSuite createTestSuite();
	
	@Override
	public TestFragment getParent() {
		// A bateria não possui parent
		return null;
	}
	
	public abstract AbstractParser getParser();
}
