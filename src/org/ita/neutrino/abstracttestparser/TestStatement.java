package org.ita.neutrino.abstracttestparser;

import org.ita.neutrino.codeparser.Statement;

public interface TestStatement extends TestElement, CodeElementWrapper<Statement> {
	
	@Override
	public TestMethod getParent();
}