package org.ita.neutrino.testsmells.smells;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class EagerTestSmell extends MethodTestCodeSmell {

	private final int maxVerificationsPerTest;
	
	@Inject
	public EagerTestSmell(
			@Named("maxVerificationsPerTest") int maxVerificationsPerTest) {
		this.maxVerificationsPerTest = maxVerificationsPerTest;
	}
	
	@Override
	public void checkForPresence(TestMethod method, MarkerManager markerManager)
			throws JavaModelException, CoreException {
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
