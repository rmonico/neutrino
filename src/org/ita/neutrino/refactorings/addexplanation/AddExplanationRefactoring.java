package org.ita.neutrino.refactorings.addexplanation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.Assertion;

public class AddExplanationRefactoring extends AbstractRefactoring {

	private String explanationString = "";
	private Assertion targetAssertion;

	public void setExplanationString(String explanationString) {
		this.explanationString = explanationString;
	}

	@Override
	public String getName() {
		return "Add explanation to assertion";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();

		if ((!(getTargetFragment() instanceof Assertion)) || (getTargetFragment() == null)) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Target is not a valid assertion."));
		} else {
			targetAssertion = (Assertion) getTargetFragment();

			if (targetAssertion.getExplanation() != null) {
				status.merge(RefactoringStatus.createFatalErrorStatus("Target assertion already have a explanation."));
			}
		}

		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		targetAssertion.setExplanation(explanationString);
		return new RefactoringStatus();
	}



}
