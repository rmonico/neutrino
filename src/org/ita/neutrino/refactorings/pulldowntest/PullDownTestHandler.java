package org.ita.neutrino.refactorings.pulldowntest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.ita.neutrino.refactorings.AbstractEclipseRefactoringCommandHandler;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.refactorings.NoInputWizard;

public class PullDownTestHandler extends AbstractEclipseRefactoringCommandHandler {

	private PullDownTestRefactoring refactoring;

	@Override
	protected String getRefactoringName() {
		return "Pull Down Test in Hierarchy";
	}

	@Override
	protected List<String> checkPreConditions() {
		// TODO stub
		List<String> lst = new ArrayList<String>();
		return lst;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		refactoring = new PullDownTestRefactoring();
		return refactoring;
	}

	@Override
	protected RefactoringWizard createRefactoringWizard(Refactoring refactoring) {
		return new NoInputWizard(refactoring, refactoring.getName());
	}
}
