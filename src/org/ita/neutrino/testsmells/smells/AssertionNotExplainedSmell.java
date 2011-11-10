package org.ita.neutrino.testsmells.smells;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.Assertion;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.addexplanation.AddExplanationAction;
import org.ita.neutrino.testsmells.core.MarkerManager;

import com.google.common.base.Strings;
import com.google.common.collect.Collections2;

@NeutrinoRefactoringForEclipse(value=AddExplanationAction.class,
		title="Add explanation",
		description="Opens the refactoring assistant to add an explanation to this assertion")
public class AssertionNotExplainedSmell implements TestCodeSmell<TestMethod> {
	
	@Override
	public void checkForPresence(TestMethod method, MarkerManager markerSink) throws JavaModelException, CoreException {
		@SuppressWarnings("unchecked")
		Collection<Assertion> allAssertions = (Collection<Assertion>)
			Collections2.filter(method.getStatements(), isAssertionFilter);
		
		if (allAssertions.size() > 1) {
			for (Assertion assertion: allAssertions) {
				if (Strings.isNullOrEmpty(assertion.getExplanation())) {
					markerSink.addMarker(assertion.getCodeElement(),
						"Assertion is missing explanation", this.getClass());
				}
			}
		}
	}
}
