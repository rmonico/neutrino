package org.ita.neutrino.refactorings.groupsimilartests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.ita.neutrino.refactorings.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.refactorings.NoInputWizard;
import org.ita.neutrino.tparsers.abstracttestparser.AbstractTestParser;

public class GroupSimilarTestsAction extends AbstractEclipseRefactoringAction {
	private GroupSimilarTestsRefactoring refactoring;
	
	@Override
	protected String getRefactoringName() {
		return "Group Similar Tests";
	}

	@Override
	protected List<String> checkPreConditions() {
		System.out.println("checkPreConditions");
		List<String> lst = new ArrayList<String>();
		return lst;
	}

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		System.out.println("createRefactoringObject");
		refactoring = new GroupSimilarTestsRefactoring();
		return refactoring;
	}

	@Override
	protected RefactoringWizard createRefactoringWizard(Refactoring refactoring) {
		return new NoInputWizard(refactoring, refactoring.getName());
	}

}
