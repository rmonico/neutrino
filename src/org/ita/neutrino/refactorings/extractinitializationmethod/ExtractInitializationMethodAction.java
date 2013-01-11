package org.ita.neutrino.refactorings.extractinitializationmethod;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.ita.neutrino.refactorings.AbstractEclipseRefactoringAction;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.refactorings.NoInputWizard;

public class ExtractInitializationMethodAction extends AbstractEclipseRefactoringAction {

	@Override
	protected String getRefactoringName() {
		return "Extract initialization method";
	}

	@Override
	protected List<String> checkPreConditions() {
		List<String> problems = new ArrayList<String>();

		// TODO: Permitir fazer essa refatoração através de uma seleção de
		// classe no package explorer
		if (!(getSelection() instanceof ITextSelection)) {
			problems.add("Select some test suite to extract a initialization method.");
		}

		return problems;
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		ExtractInitializationMethodRefactoring refactoring = new ExtractInitializationMethodRefactoring();
		
		return refactoring;
	}

	@Override
	protected RefactoringWizard createRefactoringWizard(Refactoring refactoring) {
		return new NoInputWizard(refactoring, refactoring.getName());
	}

}
