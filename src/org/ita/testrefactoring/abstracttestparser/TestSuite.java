package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

import org.ita.testrefactoring.codeparser.Type;

public abstract class TestSuite implements TestElement, CodeElementWrapper<Type> {
	
	public abstract List<Fixture> getFixtures();
	
	public abstract List<TestMethod> getTestMethodList();
	
	public abstract TestMethod getSetup();
	
	public abstract TestMethod getTeardown();

	public abstract TestMethod createTestMethod();
	
	@Override
	public abstract TestBattery getParent();
}
