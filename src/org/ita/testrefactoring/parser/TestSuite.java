package org.ita.testrefactoring.parser;

import java.util.List;

public abstract class TestSuite implements TestFragment {
	
	public abstract List<? extends Fixture> getFixtures();
	
	public abstract List<? extends TestMethod> getTestMethodList();
	
	public abstract TestMethod getSetup();
	
	public abstract TestMethod getTeardown();

	public abstract TestMethod createTestMethod();
	
	@Override
	public abstract TestBattery getParent();
}
