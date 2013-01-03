package org.ita.neutrino.refactorings.inputinitialization;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;

public class InputInitializationAction extends AbstractEclipseRefactoringAction {

	private InputInitializationRefactoring refactoring;

	@Override
	protected String getRefactoringName() {
		// TODO Auto-generated method stub
		return "Input Initialization Method";
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
		refactoring = new InputInitializationRefactoring();
		return refactoring;
	}


}
