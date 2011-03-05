package org.ita.testrefactoring.parser;

import java.util.List;

public abstract class TestSuite implements TestFragment {
	
	public abstract List<Fixture> getFixtures();
	
	public abstract List<TestMethod> getTestMethodList();
	
	public abstract TestMethod getSetup();
	
	public abstract TestMethod getTeardown();

	public abstract TestMethod createTestMethod();
	
	@Override
	public abstract TestBattery getParent();
}
