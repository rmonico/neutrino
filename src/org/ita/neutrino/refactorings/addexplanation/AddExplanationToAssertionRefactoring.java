package org.ita.neutrino.refactorings.addexplanation;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.refactorings.abstracrefactoring.RefactoringException;
import org.ita.neutrino.tparsers.abstracttestparser.Assertion;

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

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select an assert statement, press add explanation to assertion, write the explanation for the current assertion.");
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
