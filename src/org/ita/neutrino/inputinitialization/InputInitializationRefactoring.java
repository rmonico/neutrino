package org.ita.neutrino.inputinitialization;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.extractmethod.AbstractExtractMethodRefactoring;
import org.ita.neutrino.junit4parser.JUnitTestMethod;

public class InputInitializationRefactoring extends AbstractExtractMethodRefactoring {

	private JUnitTestMethod targetMethod;
	
	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof org.ita.neutrino.junit4parser.JUnitTestMethod)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a initialization test method.");
		} else {
			targetMethod = (JUnitTestMethod) getTargetFragment();
			if (! targetMethod.isAfterTestMethod()) {
				problems.add("Selection must be a test method.");
			}
		}

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select a initialization method, press input initialization, the method will be copied to the begining of each test method, the initialization method will be removed.");
		}

		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		// TODO Auto-generated method stub

	}

}
