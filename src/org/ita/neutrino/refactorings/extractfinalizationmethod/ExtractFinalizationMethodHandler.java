package org.ita.neutrino.refactorings.extractfinalizationmethod;

import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.refactorings.extractmethod.ExtractConfigurationMethodHandler;

public class ExtractFinalizationMethodHandler extends ExtractConfigurationMethodHandler {

	@Override
	protected String getRefactoringName() {
		return "Extract finalization method";
	}

	@Override
	protected AbstractRefactoring createRefactoringObject() {
		ExtractFinalizationMethodRefactoring refactoring = new ExtractFinalizationMethodRefactoring();
		
		return refactoring;
	}

}
