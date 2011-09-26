package org.ita.neutrino.groupincrementaltests;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracttestparser.AbstractTestParser;

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
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		// TODO Auto-generated method stub
		System.out.println("createRefactoringObject");
		refactoring = new GroupIncrementalTestsRefactoring();
		return refactoring;
	}

}
