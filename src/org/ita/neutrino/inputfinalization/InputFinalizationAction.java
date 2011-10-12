package org.ita.neutrino.inputfinalization;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;

public class InputFinalizationAction extends AbstractEclipseRefactoringAction {

	private InputFinalizationRefactoring refactoring;

	@Override
	protected String getRefactoringName() {
		// TODO Auto-generated method stub
		return "Input Finalization Method";
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
		refactoring = new InputFinalizationRefactoring();
		return refactoring;
	}

}
