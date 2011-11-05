package org.ita.neutrino.abstracttestparser;

import org.ita.neutrino.codeparser.Statement;

public interface TestStatement extends TestElement<Statement> {
	
	@Override
	public TestMethod getParent();
	
	public TestStatement getStatement();

	public void transformInExpression();
	
	public void transformInExpression(Action baseAction);
	
	public boolean isAssertion();
}