package org.ita.neutrino.tparsers.abstracttestparser;

import java.util.List;

import org.eclipse.ltk.core.refactoring.Change;
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

	//public abstract void applyChanges() throws TestParserException;
	
	public Change getChange();
}
