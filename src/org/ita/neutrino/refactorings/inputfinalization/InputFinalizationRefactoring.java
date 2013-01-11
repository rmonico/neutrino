package org.ita.neutrino.refactorings.inputfinalization;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.ita.neutrino.refactorings.extractmethod.AbstractExtractMethodRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class InputFinalizationRefactoring extends AbstractExtractMethodRefactoring {

	private TestMethod targetMethod;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Selection is not valid. Select a finalization test method."));
		} else {
			targetMethod = (TestMethod) getTargetFragment();
			if (! targetMethod.isAfterTestMethod()) {
				status.merge(RefactoringStatus.createFatalErrorStatus("Selection must be a test method."));
			}
		}

		if (status.hasEntries()) {
			status.merge(RefactoringStatus.createInfoStatus("Note: Select a finalization method, press input finalization, the method will be copied to the end of each test method, the finalization method will be removed."));
		}

		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		TestSuite ts = targetMethod.getParent();

		for (TestMethod tm : targetMethod.getParent().getAllTestMethodList()) {
			if (tm.isTestMethod()) {
				List<TestStatement> finalizationStatements = new ArrayList<TestStatement>();
				finalizationStatements.addAll(targetMethod.getStatements());

				tm.addStatements(finalizationStatements, tm.getStatements().size());
			}
		}

		ts.removeTestMethod(targetMethod);
		
		return new RefactoringStatus();
	}
}
