package org.ita.neutrino.tests.addexplanation;

import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.refactorings.addexplanation.AddExplanationRefactoring;
import org.ita.neutrino.tests.RefactoringAbstractTests;

public abstract class AddExplanationToAssertionTests extends RefactoringAbstractTests {

	protected AbstractRefactoring instantiateRefactoring() {
		return new AddExplanationRefactoring();
	}

	@Override
	protected void setupRefactoring() {
		((AddExplanationRefactoring) refactoring).setExplanationString("Média da turma");
	}
	
	@Override
	protected String getRefactoringName() {
		return "Adicionar explicação a asserção";
	}
}
