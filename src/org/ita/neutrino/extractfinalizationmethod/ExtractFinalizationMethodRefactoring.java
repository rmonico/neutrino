package org.ita.neutrino.extractfinalizationmethod;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.extractmethod.AbstractExtractMethodRefactoring;

public class ExtractFinalizationMethodRefactoring extends AbstractExtractMethodRefactoring {

	private TestSuite targetSuite;
	private List<TestStatement> commomStatements;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestSuite)) || (getTargetFragment() == null)) {
			problems.add("Selection must be a test suite (selection: \"" + getTargetFragment() + "\").");
		} else {
			targetSuite = (TestSuite) getTargetFragment();

			commomStatements = listCommonStatements(targetSuite, false);

			if (commomStatements.isEmpty()) {
				problems.add("Methods have no commom initialization. Unable to extract initialization method.");
			}
		}

		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {

		TestMethod afterMethod;

		if (targetSuite.getAfterMethodList().isEmpty()) {
			afterMethod = targetSuite.createNewAfterTestsMethod();
		} else {
			List<? extends TestMethod> afterMethodList = targetSuite.getAfterMethodList();
			afterMethod = afterMethodList.get(0);
		}

		afterMethod.addStatements(commomStatements, 0);

		for (TestMethod testMethod : targetSuite.getTestMethodList()) {
			testMethod.removeStatements(testMethod.getStatements().size()-commomStatements.size(), commomStatements.size());
		}
	}

}
