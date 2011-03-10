package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

import org.ita.testrefactoring.codeparser.Method;

public abstract class TestMethod implements TestElement, CodeElementWrapper<Method> {

	public abstract String getName();
	
	public abstract TestSuite getParent();

	public abstract List<? extends TestStatement> getStatements();
}
