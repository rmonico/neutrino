package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

import org.ita.testrefactoring.codeparser.Type;

public abstract class TestSuite implements TestElement, CodeElementWrapper<Type> {
	
	public abstract List<? extends Fixture> getFixtures();
	
	public abstract List<? extends TestMethod> getTestMethodList();
	
	public abstract TestMethod getBeforeMethod();
	
	public abstract TestMethod getAfterMethod();

	@Override
	public abstract TestBattery getParent();
}
