package org.ita.neutrino.refactorings.addfixture;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;

public class AddFixtureAction extends AbstractEclipseRefactoringAction  {

	private AddFixtureRefactoring refactoring;

	@Override
	protected String getRefactoringName() {
		// TODO Auto-generated method stub
		return "Add Fixture";
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
		refactoring = new AddFixtureRefactoring();
		return refactoring;
	}

}
