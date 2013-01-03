package org.ita.neutrino.refactorings.inputfinalization;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.RefactoringException;
import org.ita.neutrino.refactorings.extractmethod.AbstractExtractMethodRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class InputFinalizationRefactoring extends AbstractExtractMethodRefactoring {

	private TestMethod targetMethod;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a finalization test method.");
		} else {
			targetMethod = (TestMethod) getTargetFragment();
			if (! targetMethod.isAfterTestMethod()) {
				problems.add("Selection must be a test method.");
			}
		}

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select a finalization method, press input finalization, the method will be copied to the end of each test method, the finalization method will be removed.");
		}

		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		TestSuite ts = targetMethod.getParent();

		for (TestMethod tm : targetMethod.getParent().getAllTestMethodList()) {
			if (tm.isTestMethod()) {
				List<TestStatement> finalizationStatements = new ArrayList<TestStatement>();
				finalizationStatements.addAll(targetMethod.getStatements());

				tm.addStatements(finalizationStatements, tm.getStatements().size());
			}
		}

		ts.removeTestMethod(targetMethod);
	}
}
