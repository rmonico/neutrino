package org.ita.neutrino.refactorings.addexplanation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.ita.neutrino.refactorings.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.AbstractRefactoring;

public class AddExplanationAction extends AbstractEclipseRefactoringAction {

//	private String explanationString;
//	private AddExplanationRefactoring refactoring;

	@Override
	protected String getRefactoringName() {
		return "Add explanation to assertion";
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
		return new AddExplanationRefactoring();
	}
	
//	@Override
//	protected boolean prepareRefactoringObject() {
//		super.prepareRefactoringObject();
//		
//		InputDialog id = new InputDialog(null, "Add explanation to assertion", "Enter a explanation string", "explanation string", null);
//
//		if (id.open() == InputDialog.CANCEL) {
//			return false;
//		}
//
//		explanationString = id.getValue();
//		
//		refactoring.setExplanationString(explanationString);
//		
//		return true;
//	}

	@Override
	protected RefactoringWizard createRefactoringWizard(Refactoring refactoring) {
		return new AddExplanationWizard((AddExplanationRefactoring) refactoring, refactoring.getName());
	}
	
}
