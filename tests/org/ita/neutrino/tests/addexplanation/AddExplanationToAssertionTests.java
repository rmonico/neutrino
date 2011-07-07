package org.ita.neutrino.tests.addexplanation;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.addexplanation.AddExplanationToAssertionRefactoring;
import org.ita.neutrino.tests.RefactoringAbstractTests;

public abstract class AddExplanationToAssertionTests extends RefactoringAbstractTests {

	protected AbstractRefactoring instantiateRefactoring() {
		return new AddExplanationToAssertionRefactoring();
	}

	@Override
	protected void setupRefactoring() {
		((AddExplanationToAssertionRefactoring) refactoring).setExplanationString("Média da turma");
	}
	
	@Override
	protected String getRefactoringName() {
		return "Adicionar explicação a asserção";
	}
}
