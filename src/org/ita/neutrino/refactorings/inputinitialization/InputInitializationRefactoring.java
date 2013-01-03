package org.ita.neutrino.refactorings.inputinitialization;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.RefactoringException;
import org.ita.neutrino.refactorings.extractmethod.AbstractExtractMethodRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class InputInitializationRefactoring extends AbstractExtractMethodRefactoring {

	private TestMethod targetMethod;
	
	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a initialization test method.");
		} else {
			targetMethod = (TestMethod) getTargetFragment();
			if (! targetMethod.isBeforeTestMethod()) {
				problems.add("Selection must be a before test method.");
			}
		}

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select a initialization method, press input initialization, the method will be copied to the begining of each test method, the initialization method will be removed.");
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
				
				tm.addStatements(finalizationStatements, 0);
			}
		}

		ts.removeTestMethod(targetMethod);
	}

}
