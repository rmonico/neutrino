package org.ita.neutrino.tests.extractinitializationmethod;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.extractinitializationmethod.ExtractInitializationMethodRefactoring;
import org.ita.neutrino.tests.RefactoringAbstractTests;

public abstract class ExtractInitializationMethodTests extends RefactoringAbstractTests {
	
	protected AbstractRefactoring instantiateRefactoring() {
		return new ExtractInitializationMethodRefactoring();
	}
	
	@Override
	protected String getRefactoringName() {
		return "Extrair método de inicialização";
	}
}
