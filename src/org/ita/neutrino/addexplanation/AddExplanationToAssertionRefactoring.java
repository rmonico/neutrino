package org.ita.neutrino.addexplanation;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.Assertion;

public class AddExplanationToAssertionRefactoring extends AbstractRefactoring {
	
	private String explanationString;
	private Assertion targetAssertion;
	
	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();
		
		if ((!(getTargetFragment() instanceof Assertion)) || (getTargetFragment() == null)) {
			problems.add("Target is not a valid assertion.");
		} else {
			targetAssertion = (Assertion) getTargetFragment();

			if (targetAssertion.getExplanation() != null) {
				problems.add("Target assertion still have a explanation.");
			}
		}

		
		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		targetAssertion.setExplanation(explanationString);
	}

	public void setExplanationString(String explanationString) {
		this.explanationString = explanationString;
	}

}
