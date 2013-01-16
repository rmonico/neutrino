package org.ita.neutrino.refactorings.groupincrementaltests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.ita.neutrino.refactorings.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.refactorings.NoInputWizard;

public class GroupIncrementalTestsAction extends AbstractEclipseRefactoringAction {
	private GroupIncrementalTestsRefactoring refactoring;
	@Override
	protected String getRefactoringName() {
		// TODO Auto-generated method stub
		return "Group Incremental Tests";
	}

	@Override
	protected List<String> checkPreConditions() {
		// TODO Auto-generated method stub
		List<String> lst = new ArrayList<String>();
		return lst;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		// TODO Auto-generated method stub
		refactoring = new GroupIncrementalTestsRefactoring();
		return refactoring;
	}

	@Override
	protected RefactoringWizard createRefactoringWizard(Refactoring refactoring) {
		return new NoInputWizard(refactoring, refactoring.getName());
	}

}
