package org.ita.neutrino.refactorings.groupsimilartests;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
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

}
