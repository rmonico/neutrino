package org.ita.neutrino.refactorings.extractfinalizationmethod;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.ita.neutrino.codeparser.astparser.ASTMethodInvocationStatement;
import org.ita.neutrino.refactorings.extractmethod.AbstractExtractMethodRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class ExtractFinalizationMethodRefactoring extends AbstractExtractMethodRefactoring {

	private TestSuite targetSuite;
	private List<TestStatement> commomStatements;

	private void removeInvalidStatements() {
		// Only statements after assignement which are expressions, should be moved.
		if (commomStatements != null && commomStatements.size() > 0) {
			int i = 0;
			for (i = commomStatements.size() - 1; i > -1; i--) {
				if (commomStatements.get(i).getCodeElement() instanceof ASTMethodInvocationStatement) {
					ASTMethodInvocationStatement statement = (ASTMethodInvocationStatement) commomStatements.get(i).getCodeElement();
					if (statement.isAssert()) {
						break;
					}
				}
			}
			if (i > -1) {
				for (int j = i; j > -1; j--) {
					commomStatements.remove(j);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "Extract finalization method";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		
		if ((!(getTargetFragment() instanceof TestSuite)) || (getTargetFragment() == null)) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Selection must be a test suite (selection: \"" + getTargetFragment() + "\")."));
		} else {
			targetSuite = (TestSuite) getTargetFragment();

			commomStatements = listCommonStatements(targetSuite, false);

			removeInvalidStatements();

			if (commomStatements.isEmpty()) {
				status.merge(RefactoringStatus.createFatalErrorStatus("Methods have no commom finalization. Unable to extract finalization method."));
			}
		}
		
		if (status.hasEntries()) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Note: Select the class name of the test suite, press extract finalization method, all test methods must have at least the last line in commom, the commom lines up to the last assert will be extracted to a tear down method."));
		}
		
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		TestMethod afterMethod;

		if (targetSuite.getAfterMethodList().isEmpty()) {
			afterMethod = targetSuite.createNewAfterTestsMethod();
		} else {
			List<? extends TestMethod> afterMethodList = targetSuite.getAfterMethodList();
			afterMethod = afterMethodList.get(0);
		}

		afterMethod.addStatements(commomStatements, 0);

		for (TestMethod testMethod : targetSuite.getTestMethodList()) {
			testMethod.removeStatements(testMethod.getStatements().size() - commomStatements.size(), commomStatements.size());
		}
		
		return new RefactoringStatus();
	}

}
