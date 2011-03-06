package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

public abstract class TestSuite implements TestElement {
	
	public abstract List<Fixture> getFixtures();
	
	public abstract List<TestMethod> getTestMethodList();
	
	public abstract TestMethod getSetup();
	
	public abstract TestMethod getTeardown();

	public abstract TestMethod createTestMethod();
	
	@Override
	public abstract TestBattery getParent();
}
