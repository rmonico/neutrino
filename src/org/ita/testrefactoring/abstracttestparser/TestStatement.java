package org.ita.testrefactoring.abstracttestparser;

public interface TestStatement extends TestElement {
	
	@Override
	public TestMethod getParent();
}
