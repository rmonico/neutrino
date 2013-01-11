package org.ita.neutrino.refactorings.groupincrementaltests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.ita.neutrino.codeparser.astparser.ASTMethodInvocationStatement;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitAssertion;

public class GroupIncrementalTestsRefactoring extends AbstractRefactoring {
	private TestMethod targetMethod;
	private List<TestMethod> commonTestMethod;

	private void groupAsserts() {
		int originalCursor = 0;
		int modifiedCursor = 0;
		for (TestMethod tm : commonTestMethod) {
			List<TestStatement> temp = new ArrayList<TestStatement>();
			temp.addAll(tm.getStatements());

			for (int i = originalCursor; i < temp.size(); i++) {
				if (temp.get(i).equals(targetMethod.getStatements().get(i))) {
					// Statements already in there.
					originalCursor++;
				} else {
					// Insert Asserts in the right place.
					List<TestStatement> insert = new ArrayList<TestStatement>();
					insert.add(temp.get(i));
					targetMethod.addStatements(insert, modifiedCursor);
				}
				modifiedCursor++;
			}
		}
	}

	private List<TestMethod> getCommomTestMethod() {
		List<TestMethod> commomTestMethod = new ArrayList<TestMethod>();
		List<TestMethod> sortedMethods = new ArrayList<TestMethod>();
		List<TestStatement> commomStatements = new ArrayList<TestStatement>();

		commomStatements.addAll(targetMethod.getStatements());
		sortedMethods.addAll(targetMethod.getParent().getTestMethodList());

		Collections.sort(sortedMethods);
		for (TestMethod tm : sortedMethods) {
			if (targetMethod.getStatements().size() < tm.getStatements().size()) {
				break;
			}
			if (!tm.getName().equals(targetMethod.getName())) {
				List<TestStatement> temp = new ArrayList<TestStatement>();
				temp.addAll(tm.getStatements());
				boolean reachAssert = false;
				boolean isCommom = true;
				for (int i = 0; i < temp.size(); i++) {
					if (!temp.get(i).equals(commomStatements.get(i))) {
						if (temp.get(i) instanceof JUnitAssertion || (temp.get(i) instanceof ASTMethodInvocationStatement && ((ASTMethodInvocationStatement) temp.get(i)).isAssert())) {
							reachAssert = true;
						} else {
							isCommom = false;
							break;
						}
					} else if (reachAssert) {
						isCommom = false;
						break;
					}
				}
				if (isCommom) {
					commomTestMethod.add(tm);
				}
			}
		}
		return commomTestMethod;
	}

	@Override
	public String getName() {
		return "Group incremental tests";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Selection is not valid. Select a method."));
		} else {
			targetMethod = (TestMethod) getTargetFragment();

			commonTestMethod = getCommomTestMethod();
			if (commonTestMethod.size() < 1) {
				status.merge(RefactoringStatus.createFatalErrorStatus("There is no common interaction tests"));
			}
		}

		if (status.hasEntries()) {
			status.merge(RefactoringStatus.createInfoStatus("Note: Select the bigger method wich groups smaller ones, press group incremental tests, all asserts will be brought to the selected method, the smaller methods will be removed."));
		}
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		if (commonTestMethod != null && commonTestMethod.size() > 0) {
			groupAsserts();

			TestSuite ts = targetMethod.getParent();

			// remove inner tests.
			for (TestMethod tm : commonTestMethod) {
				ts.removeTestMethod(tm);
			}
		}
		
		return new RefactoringStatus();
	}
}
