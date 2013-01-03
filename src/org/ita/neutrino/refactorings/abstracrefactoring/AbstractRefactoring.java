package org.ita.neutrino.refactorings.abstracrefactoring;

import java.util.List;

import org.ita.neutrino.tparsers.abstracttestparser.TestBattery;
import org.ita.neutrino.tparsers.abstracttestparser.TestElement;
import org.ita.neutrino.tparsers.abstracttestparser.TestParserException;

public abstract class AbstractRefactoring {

	private TestBattery battery;
	private TestElement<?> fragment;

	public void setBattery(TestBattery battery) {
		this.battery = battery;
	}

	protected TestBattery getBattery() {
		return battery;
	}

	public void refactor() throws RefactoringException {
		List<String> problems = checkInitialConditions();

		if ((problems!= null) && (problems.size() > 0)) {
			throw new RefactoringException(problems);
		}

		doRefactor();
		
		applyChanges();
	}

	/**
	 * Checagem de condições iniciais da refatoração. Deve ser desacoplada do
	 * framework do código de testes.
	 * 
	 * @return
	 */
	public abstract List<String> checkInitialConditions();

	protected abstract void doRefactor() throws RefactoringException;

	public void setTargetFragment(TestElement<?> fragment) {
		this.fragment = fragment;
	}

	public TestElement<?> getTargetFragment() {
		return fragment;
	}

	private void applyChanges() throws RefactoringException {
		try {
			battery.applyChanges();
		} catch (TestParserException e) {
			throw new RefactoringException(e);
		}
	}
}
