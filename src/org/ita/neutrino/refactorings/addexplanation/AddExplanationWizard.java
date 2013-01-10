package org.ita.neutrino.refactorings.addexplanation;

import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

public class AddExplanationWizard extends RefactoringWizard {
	public AddExplanationWizard(AddExplanationRefactoring refactoring, String pageTitle) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		setDefaultPageTitle(pageTitle);
	}

	@Override
	protected void addUserInputPages() {
		addPage(new AddExplanationRefactoringInputPage("TestRefactoringInputPage"));
	}
}
