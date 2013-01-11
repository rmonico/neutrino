package org.ita.neutrino.refactorings.decomposeassertion;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.Assertion;

public class DecomposeAssertionRefactoring extends AbstractRefactoring {

	private Assertion targetAssertion;
	
	@Override
	public String getName() {
		return "Decompose assertion";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		
		if ((!(getTargetFragment() instanceof Assertion)) || (getTargetFragment() == null)) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Target is not a valid assertion."));
		} else {
			targetAssertion = (Assertion) getTargetFragment();

			if (!targetAssertion.hasMultipleChecks()) {
				status.merge(RefactoringStatus.createFatalErrorStatus("Target assertion must have multiple checks to decompose."));
			}
		}

		if (status.hasEntries()) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Note: Select an assert statement, press add decompose assertion."));
		}
		
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		targetAssertion.decomposeAssertion();
		return new RefactoringStatus();
	}

	
}
