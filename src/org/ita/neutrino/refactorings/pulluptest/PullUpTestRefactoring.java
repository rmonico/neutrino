package org.ita.neutrino.refactorings.pulluptest;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.refactorings.abstracrefactoring.RefactoringException;
import org.ita.neutrino.tparsers.abstracttestparser.TestBattery;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class PullUpTestRefactoring extends AbstractRefactoring {
	
	private TestMethod targetMethod;
	
	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a finalization test method.");
		} else {
			targetMethod = (TestMethod) getTargetFragment();
			if (! targetMethod.isTestMethod()) {
				problems.add("Selection must be a test method.");
			} else {
				TestSuite superSuite = targetMethod.getParent().getSuper();
				if(superSuite == null)
					problems.add("The selected method is already in the top of the hierarchy.");
				else {
					if(targetMethod.getParent().getSuper().getMethodByName(targetMethod.getName()) == null)
						problems.add("The superclass doesn't have the " + targetMethod.getName() + " abstract method.");
				}
			}
		}

		return problems;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doRefactor() throws RefactoringException {
		TestSuite currentSuite = targetMethod.getParent();
		TestBattery testBattery = currentSuite.getParent();
		
		for(TestSuite ts : testBattery.getSuiteList()) {
			for(TestMethod tm : (List<TestMethod>) ts.getTestMethodList()) {
				if(tm.getName().equals(targetMethod.getName()) && !ts.equals(currentSuite)) 
					ts.removeTestMethod(tm);
			}
		}
		
		TestMethod pulledUpMethod = targetMethod.getParent().getSuper().createNewTestMethod(targetMethod.getName());
		
		List<TestStatement> methodStatements = new ArrayList<TestStatement>();
		methodStatements.addAll(targetMethod.getStatements());

		targetMethod.getParent().removeTestMethod(targetMethod);
		pulledUpMethod.addStatements(methodStatements, 0);
	}
	
}
