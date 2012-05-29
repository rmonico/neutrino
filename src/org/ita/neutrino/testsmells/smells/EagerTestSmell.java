package org.ita.neutrino.testsmells.smells;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.splitincrementaltests.SplitIncrementalTestsAction;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@NeutrinoRefactoringForEclipse(description = "Splits this test into multiple ones",
		title="Split test",
		value=SplitIncrementalTestsAction.class)
public class EagerTestSmell implements TestCodeSmell<TestMethod> {

	private final int maxVerificationsPerTest;
	private final int requiredNumberOfTestStatements;
	
	@Inject
	public EagerTestSmell(
			@Named("maxVerificationsPerTest") int maxVerificationsPerTest,
			@Named("requiredNumberOfTestStatements") int requiredNumberOfTestStatements) {
		this.maxVerificationsPerTest = maxVerificationsPerTest;
		this.requiredNumberOfTestStatements = requiredNumberOfTestStatements;
	}
	
	@Override
	public void checkForPresence(TestMethod method, MarkerManager markerManager)
			throws JavaModelException, CoreException {
		if (method.getStatements().size() < this.requiredNumberOfTestStatements) {
			return;
		}
		if (getApproximateNumberOfVerifications(method) > this.maxVerificationsPerTest) {
			markerManager.addMarker(method.getCodeElement(),
					"Eager test (too much functionality being assessed in a single test)",
					this.getClass());
		}
	}

	public int getApproximateNumberOfVerifications(TestMethod testMethod) {
		final int STIMULATION = 1;
		final int VERIFICATION = 2;
		int currentState = STIMULATION;
		int verificationsFoundSoFar = 0;
		
		for (TestStatement statement : testMethod.getStatements()) {
			if (statement.isAssertion()) {
				if (currentState != VERIFICATION) {
					currentState = VERIFICATION;
					++verificationsFoundSoFar;
				}
			} else {
				currentState = STIMULATION;
			}
		}
		
		return verificationsFoundSoFar;
	}
}
