package org.ita.testrefactoring.abstracttestparser;

import java.util.List;

import org.ita.testrefactoring.codeparser.Environment;

public abstract class TestBattery implements TestElement, CodeElementWrapper<Environment> {

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
	
	public abstract TestSelection getSelection();
	
	@Override
	public abstract Environment getCodeElement();

	public abstract void applyChanges();
	
}
