package org.ita.neutrino.inputinitialization;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.extractmethod.AbstractExtractMethodRefactoring;

public class InputInitializationRefactoring extends AbstractExtractMethodRefactoring {
	//private JUnitTestMethod targetMethod;
	
	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		

		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		// TODO Auto-generated method stub

	}

}
