package org.ita.testrefactoring;

import org.ita.testrefactoring.abstracttestparser.TestBattery;
import org.ita.testrefactoring.abstracttestparser.TestFragment;

public abstract class AbstractRefactoring {

	private TestBattery battery;
	private TestFragment fragment;

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

	public void setTargetFragment(TestFragment fragment) {
		this.fragment = fragment;
	}

	public TestFragment getTargetFragment() {
		return fragment;
	}
}
