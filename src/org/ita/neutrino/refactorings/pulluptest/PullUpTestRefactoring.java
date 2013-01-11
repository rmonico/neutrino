package org.ita.neutrino.refactorings.pulluptest;

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

public class PullUpTestRefactoring extends AbstractRefactoring {
	
	private TestMethod targetMethod;

	@Override
	public String getName() {
		return "Pull up test";
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
				TestSuite superSuite = targetMethod.getParent().getSuper();
				if(superSuite == null)
					status.merge(RefactoringStatus.createFatalErrorStatus("The selected method is already in the top of the hierarchy."));
				else {
					if(targetMethod.getParent().getSuper().getMethodByName(targetMethod.getName()) == null)
						status.merge(RefactoringStatus.createFatalErrorStatus("The superclass doesn't have the " + targetMethod.getName() + " abstract method."));
				}
			}
		}

		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
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
		
		return new RefactoringStatus();
	}
	
}
