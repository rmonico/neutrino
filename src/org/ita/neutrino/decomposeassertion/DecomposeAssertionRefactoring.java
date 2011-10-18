package org.ita.neutrino.decomposeassertion;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.Assertion;

public class DecomposeAssertionRefactoring extends AbstractRefactoring {

	private Assertion targetAssertion;
	
	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof Assertion)) || (getTargetFragment() == null)) {
			problems.add("Target is not a valid assertion.");
		} else {
			targetAssertion = (Assertion) getTargetFragment();

			if (!targetAssertion.hasMultipleChecks()) {
				problems.add("Target assertion must have multiple checks to decompose.");
			}
		}

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select an assert statement, press add decompose assertion.");
		}
		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		targetAssertion.decomposeAssertion();

	}

}
