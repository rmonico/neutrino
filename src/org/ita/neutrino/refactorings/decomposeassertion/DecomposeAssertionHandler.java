package org.ita.neutrino.refactorings.decomposeassertion;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.ita.neutrino.refactorings.AbstractEclipseRefactoringCommandHandler;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.refactorings.NoInputWizard;

public class DecomposeAssertionHandler extends AbstractEclipseRefactoringCommandHandler {

	private DecomposeAssertionRefactoring refactoring;

	@Override
	protected String getRefactoringName() {
		return "Decompose Assertion";
	}

	@Override
	protected List<String> checkPreConditions() {
		List<String> lst = new ArrayList<String>();
		return lst;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		refactoring = new DecomposeAssertionRefactoring();
		return refactoring;
	}

	@Override
	protected RefactoringWizard createRefactoringWizard(Refactoring refactoring) {
		return new NoInputWizard(refactoring, refactoring.getName());
	}

}
