package org.ita.neutrino.refactorings.decomposeassertion;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.refactorings.abstracrefactoring.NoInputWizard;

public class DecomposeAssertionAction extends AbstractEclipseRefactoringAction {

	private DecomposeAssertionRefactoring refactoring;

	@Override
	protected String getRefactoringName() {
		return "Decompose Assertion";
	}

	@Override
	protected List<String> checkPreConditions() {
		System.out.println("checkPreConditions");
		List<String> lst = new ArrayList<String>();
		return lst;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		System.out.println("createRefactoringObject");
		refactoring = new DecomposeAssertionRefactoring();
		return refactoring;
	}

	@Override
	protected RefactoringWizard createRefactoringWizard(Refactoring refactoring) {
		return new NoInputWizard(refactoring, refactoring.getName());
	}

}
