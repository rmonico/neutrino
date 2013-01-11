package org.ita.neutrino.refactorings.splitincrementaltests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.Assertion;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class SplitIncrementalTestsRefactoring extends AbstractRefactoring {
	private TestMethod targetMethod;
	private int index = 1;

	private void createMethod(List<TestStatement> statements) {
		boolean canRemove = false;
		for (int i = statements.size() - 1; i >= 0; i--) {
			if (statements.get(i) instanceof Assertion) {
				if (canRemove) {
					statements.remove(i);
				}
			} else {
				canRemove = true;
			}
		}

		List<TestStatement> copied = new ArrayList<TestStatement>();
		copied.addAll(statements);

		String newMethodName = null;
		List<? extends TestMethod> methods = targetMethod.getParent().getAllTestMethodList();
		for (int i = index; i < Integer.MAX_VALUE;) {
			index++;
			newMethodName = targetMethod.getName() + i;
			for (TestMethod tm : methods) {
				if (tm.getName().equals(newMethodName)) {
					continue;
				}
			}
			break;
		}

		TestMethod tm = targetMethod.getParent().createNewTestMethod(newMethodName);
		tm.addStatements(copied, 0);
	}

	@Override
	public String getName() {
		return "Split incremental tests";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {

		RefactoringStatus status = new RefactoringStatus();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Selection is not valid. Select a method."));
		} else {
			targetMethod = (TestMethod) getTargetFragment();
		}

		if (status.hasEntries()) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Note: Select the bigger method wich groups smaller ones, press group incremental tests, all asserts will be brought to the selected method, the smaller methods will be removed."));
		}
		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		List<TestStatement> commomStatements = new ArrayList<TestStatement>();
		List<TestStatement> temp = new ArrayList<TestStatement>();
		commomStatements.addAll(targetMethod.getStatements());
		List<Integer> asserts = new ArrayList<Integer>();
		boolean lastWasAssert = false;

		for (int i = 0; i < commomStatements.size(); i++) {
			if (commomStatements.get(i) instanceof Assertion) {
				asserts.add(i);
				lastWasAssert = true;
			} else {
				if (lastWasAssert) {
					createMethod(temp);
				}
				lastWasAssert = false;
			}
			temp.add(commomStatements.get(i));
		}
		if (lastWasAssert) {
			createMethod(commomStatements);
		}
		
		TestSuite ts = targetMethod.getParent();
		ts.removeTestMethod(targetMethod);
		
		return new RefactoringStatus();
	}
}
