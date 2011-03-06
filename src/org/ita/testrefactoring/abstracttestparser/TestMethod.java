package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

public abstract class TestMethod implements TestElement {

	public abstract List<TestStatement> getElements();

	public abstract Action createAction();

	public abstract Assertion createAssertion();
}
