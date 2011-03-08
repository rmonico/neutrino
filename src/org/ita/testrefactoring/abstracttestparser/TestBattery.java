package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

public abstract class TestBattery implements TestElement {

	public abstract List<? extends TestSuite> getSuiteList();

	/**
	 * Deve devolver uma suite de testes baseado no nome da mesma.
	 * 
	 * @return
	 */
	public abstract TestSuite getSuiteByName(String suiteName);

	@Override
	public TestElement getParent() {
		// A bateria não possui parent
		return null;
	}
}
