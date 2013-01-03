package org.ita.neutrino.tests.extractfinalizationmethod;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.refactorings.extractfinalizationmethod.ExtractFinalizationMethodRefactoring;
import org.ita.neutrino.tests.RefactoringAbstractTests;

public abstract class ExtractFinalizationMethodTests extends RefactoringAbstractTests {
	
	protected AbstractRefactoring instantiateRefactoring() {
		return new ExtractFinalizationMethodRefactoring();
	}
	
	@Override
	protected String getRefactoringName() {
		return "Extrair método de finalização";
	}
}
