package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

public abstract class TestMethod implements TestFragment {

	public abstract List<TestElement> getElements();

	public abstract Action createAction();

	public abstract Assertion createAssertion();
}