package org.ita.testrefactoring.abstracttestparser;

import org.ita.testrefactoring.codeparser.Statement;

public interface TestStatement<T extends Statement> extends TestElement, CodeElementWrapper<T> {
	
	@Override
	public TestMethod getParent();
}
