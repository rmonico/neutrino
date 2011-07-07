package org.ita.neutrino.extractinitializationmethod;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.extractmethod.AbstractExtractMethodRefactoring;

public class ExtractInitializationMethodRefactoring extends AbstractExtractMethodRefactoring {

	private TestSuite targetSuite;
	private List<TestStatement> commomStatements;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestSuite)) || (getTargetFragment() == null)) {
			problems.add("Selection must be a test suite (selection: \"" + getTargetFragment() + "\").");
		} else {
			targetSuite = (TestSuite) getTargetFragment();

			commomStatements = listCommonStatements(targetSuite, true);
			
			if (commomStatements.isEmpty()) {
				problems.add("Methods have no commom initialization. Unable to extract initialization method.");
			}
		}

		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		
		TestMethod beforeMethod;
		
		if (targetSuite.getBeforeMethodList().isEmpty()) {
			beforeMethod = targetSuite.createNewBeforeTestsMethod();
		} else {
			List<? extends TestMethod> beforeMethodList = targetSuite.getBeforeMethodList();
			beforeMethod = beforeMethodList.get(beforeMethodList.size()-1);
		}

		// adiciona os novos statements ao final do método
		beforeMethod.addStatements(commomStatements, -1);
		
		for (TestMethod testMethod : targetSuite.getTestMethodList()) {
			testMethod.removeStatements(0, commomStatements.size());
		}
	}

}
