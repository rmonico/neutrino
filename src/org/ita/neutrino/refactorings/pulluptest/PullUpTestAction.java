package org.ita.neutrino.refactorings.pulluptest;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;

public class PullUpTestAction extends AbstractEclipseRefactoringAction {

	private PullUpTestRefactoring refactoring;

	@Override
	protected String getRefactoringName() {
		return "Pull Up Test in Hierarchy";
	}

	@Override
	protected List<String> checkPreConditions() {
		System.out.println("checkPreConditions");
		// TODO stub
		List<String> lst = new ArrayList<String>();
		return lst;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		System.out.println("createRefactoringObject");
		refactoring = new PullUpTestRefactoring();
		return refactoring;
	}
}
