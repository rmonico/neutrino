package org.ita.neutrino.addexplanation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.ITextSelection;
import org.ita.neutrino.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;

public abstract class AddExplanationAction extends AbstractEclipseRefactoringAction {

	private String explanationString;
	private AddExplanationToAssertionRefactoring refactoring;

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
		return refactoring = new AddExplanationToAssertionRefactoring();
	}
	
	@Override
	protected boolean prepareRefactoringObject() {
		super.prepareRefactoringObject();
		
		InputDialog id = new InputDialog(null, "Add explanation to assertion", "Enter a explanation string", "explanation string", null);

		if (id.open() == InputDialog.CANCEL) {
			return false;
		}

		explanationString = id.getValue();
		
		refactoring.setExplanationString(explanationString);
		
		return true;
	}
	
}
