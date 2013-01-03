package org.ita.neutrino.refactorings.splitclass;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextSelection;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;

public class SplitClassAction  extends AbstractEclipseRefactoringAction  {
	private SplitClassRefactoring refactoring;
	
	@Override
	protected String getRefactoringName() {
		return "Split test class";
	}

	@Override
	protected List<String> checkPreConditions() {
		List<String> problems = new ArrayList<String>();
		if (!(getSelection() instanceof ITextSelection)) {
			problems.add("Select some assertion to refactor.");
		}

		return problems;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		refactoring = new SplitClassRefactoring();

		

		
		
		return refactoring;
	}

}
