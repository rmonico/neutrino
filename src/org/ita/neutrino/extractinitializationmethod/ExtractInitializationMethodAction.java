package org.ita.neutrino.extractinitializationmethod;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextSelection;
import org.ita.neutrino.abstracrefactoring.AbstractEclipseRefactoringAction;
import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;

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

}
