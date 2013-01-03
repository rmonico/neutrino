package org.ita.neutrino.refactorings.pulldowntest;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.refactorings.abstracrefactoring.RefactoringException;
import org.ita.neutrino.tparsers.abstracttestparser.TestBattery;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class PullDownTestRefactoring  extends AbstractRefactoring {
	
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
				TestSuite testSuite = targetMethod.getParent();
				TestBattery testBattery = testSuite.getParent();
				
				boolean foundSubSuite = false;
				for(TestSuite ts : testBattery.getSuiteList()) {
					TestSuite superSuite = ts.getSuper();
					if(superSuite != null) {
						if(superSuite.equals(testSuite)) {
							foundSubSuite = true;
							break;
						}
					}
				}
				
				if(!foundSubSuite) 
					problems.add("The selected test suite has no subsuite.");
			}
		}

		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		TestSuite currentSuite = targetMethod.getParent();
		TestBattery testBattery = currentSuite.getParent();
		
		for(TestSuite ts : testBattery.getSuiteList()) {
			TestSuite superSuite = ts.getSuper();
			if(superSuite != null && !ts.equals(currentSuite))
				if(superSuite.equals(currentSuite)) {
					TestMethod pulledDownMethod = ts.createNewTestMethod(targetMethod.getName());
					
					List<TestStatement> methodStatements = new ArrayList<TestStatement>();
					methodStatements.addAll(targetMethod.getStatements());

					pulledDownMethod.addStatements(methodStatements, 0);
				}
		}
		
		targetMethod.setAbstract();
	}
	
}
