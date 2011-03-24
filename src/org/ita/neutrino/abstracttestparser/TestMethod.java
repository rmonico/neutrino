package org.ita.neutrino.abstracttestparser;

import java.util.List;

import org.ita.neutrino.codeparser.Method;

public abstract class TestMethod implements TestElement, CodeElementWrapper<Method> {

	public abstract String getName();
	
	public abstract TestSuite getParent();

	public abstract List<? extends TestStatement> getStatements();
}
