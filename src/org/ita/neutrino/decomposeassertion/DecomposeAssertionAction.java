package org.ita.neutrino.decomposeassertion;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;

public abstract class DecomposeAssertionAction extends AbstractEclipseRefactoringAction {

	private DecomposeAssertionRefactoring refactoring;

	@Override
	protected String getRefactoringName() {
		// TODO Auto-generated method stub
		return "Decompose Assertion";
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
		refactoring = new DecomposeAssertionRefactoring();
		return refactoring;
	}

}
