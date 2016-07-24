package org.ita.neutrino.refactorings.extractinitializationmethod;

import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.refactorings.extractmethod.ExtractConfigurationMethodHandler;

public class ExtractInitializationMethodHandler extends ExtractConfigurationMethodHandler {

	@Override
	protected String getRefactoringName() {
		return "Extract initialization method";
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		ExtractInitializationMethodRefactoring refactoring = new ExtractInitializationMethodRefactoring();
		
		return refactoring;
	}

}
