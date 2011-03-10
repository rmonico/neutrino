package org.ita.testrefactoring.abstracttestparser;

import org.ita.testrefactoring.codeparser.Statement;

public interface TestStatement extends TestElement, CodeElementWrapper<Statement> {
	
	@Override
	public TestMethod getParent();
}