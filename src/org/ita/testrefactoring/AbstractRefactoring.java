package org.ita.testrefactoring;

import org.ita.testrefactoring.abstracttestparser.TestBattery;
import org.ita.testrefactoring.abstracttestparser.TestElement;

public abstract class AbstractRefactoring {

	private TestBattery battery;
	private TestElement fragment;

	public void setBattery(TestBattery battery) {
		this.battery = battery;
	}

	protected TestBattery getTestBattery() {
		return battery;
	}

	public void refactor() throws RefactoringException {
		InitialConditionNotMet error = checkInitialConditions();

		if (error != null) {
			throw new RefactoringException(error);
		}

		doRefactor();
	}

	public abstract InitialConditionNotMet checkInitialConditions();

	protected abstract void doRefactor() throws RefactoringException;

	public void setTargetFragment(TestElement fragment) {
		this.fragment = fragment;
	}

	public TestElement getTargetFragment() {
		return fragment;
	}
}
