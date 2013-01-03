package org.ita.neutrino.refactorings.groupincrementaltests;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;

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
		System.out.println("checkPreConditions");
		List<String> lst = new ArrayList<String>();
		return lst;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		// TODO Auto-generated method stub
		System.out.println("createRefactoringObject");
		refactoring = new GroupIncrementalTestsRefactoring();
		return refactoring;
	}

}
