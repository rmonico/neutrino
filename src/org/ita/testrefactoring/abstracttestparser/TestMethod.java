package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

import org.ita.testrefactoring.codeparser.Method;

public abstract class TestMethod implements TestElement, CodeElementWrapper<Method> {

	public abstract TestSuite getParent();

	public abstract List<TestStatement> getElements();

	public abstract Action createAction();

	public abstract Assertion createAssertion();

	public abstract String getName();
}
