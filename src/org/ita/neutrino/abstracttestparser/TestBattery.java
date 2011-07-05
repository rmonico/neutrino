package org.ita.neutrino.abstracttestparser;

import java.util.List;

import org.ita.neutrino.codeparser.Environment;

public interface TestBattery extends TestElement<Environment> {

	public abstract List<? extends TestSuite> getSuiteList();

	/**
	 * Deve devolver uma suite de testes baseado no nome da mesma.
	 * 
	 * @return
	 */
	public abstract TestSuite getSuiteByName(String suiteName);

	public abstract TestSelection getSelection();
	
	@Override
	public abstract Environment getCodeElement();

	public abstract void applyChanges() throws TestParserException;
	
}
