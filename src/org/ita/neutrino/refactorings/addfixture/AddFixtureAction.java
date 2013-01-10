package org.ita.neutrino.refactorings.addfixture;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.refactorings.abstracrefactoring.NoInputWizard;

public class AddFixtureAction extends AbstractEclipseRefactoringAction  {

	@Override
	protected String getRefactoringName() {
		return "Add Fixture";
	}

	@Override
	protected List<String> checkPreConditions() {
		List<String> problems = new ArrayList<String>();
		if (!(getSelection() instanceof ITextSelection)) {
			problems.add("Select some instance to refactor.");
		}

		return problems;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		return new AddFixtureRefactoring();
	}

	@Override
	protected RefactoringWizard createRefactoringWizard(Refactoring refactoring) {
		return new NoInputWizard(refactoring, refactoring.getName());
	}

}
