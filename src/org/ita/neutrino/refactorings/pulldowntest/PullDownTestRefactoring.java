package org.ita.neutrino.refactorings.pulldowntest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestBattery;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class PullDownTestRefactoring  extends AbstractRefactoring {
	
	private TestMethod targetMethod;

	@Override
	public String getName() {
		return "Pull down test";
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		
		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			status.merge(RefactoringStatus.createFatalErrorStatus("Selection is not valid. Select a finalization test method."));
		} else {
			targetMethod = (TestMethod) getTargetFragment();
			if (! targetMethod.isTestMethod()) {
				status.merge(RefactoringStatus.createFatalErrorStatus("Selection must be a test method."));
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
					status.merge(RefactoringStatus.createFatalErrorStatus("The selected test suite has no subsuite."));
			}
		}

		return status;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
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
		
		return new RefactoringStatus();
	}
	
}
