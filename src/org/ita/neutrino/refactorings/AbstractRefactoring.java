package org.ita.neutrino.refactorings;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestBattery;
import org.ita.neutrino.tparsers.abstracttestparser.TestElement;

public abstract class AbstractRefactoring extends Refactoring {

	private TestBattery battery;
	private TestElement<?> fragment;

	public void setBattery(TestBattery battery) {
		this.battery = battery;
	}

	protected TestBattery getBattery() {
		return battery;
	}

//	public void refactor() throws RefactoringException {
//		List<String> problems = checkInitialConditions();
//
//		if ((problems!= null) && (problems.size() > 0)) {
//			throw new RefactoringException(problems);
//		}
//
//		doRefactor();
//		
//		//applyChanges();
//	}

	/**
	 * Checagem de condições iniciais da refatoração. Deve ser desacoplada do
	 * framework do código de testes.
	 * 
	 * @return
	 */
//	public abstract List<String> checkInitialConditions();

//	protected abstract void doRefactor() throws RefactoringException;

	public void setTargetFragment(TestElement<?> fragment) {
		this.fragment = fragment;
	}

	public TestElement<?> getTargetFragment() {
		return fragment;
	}

//	private void applyChanges() throws RefactoringException {
//		try {
//			battery.applyChanges();
//		} catch (TestParserException e) {
//			throw new RefactoringException(e);
//		}
//	}
	
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return battery.getChange();
	}
}
