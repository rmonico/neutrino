package org.ita.neutrino.generictestparser;

import org.ita.neutrino.abstracttestparser.TestSelection;
import org.ita.neutrino.abstracttestparser.TestSuite;

public final class TestFrameworkParsingResults {
	public TestFrameworkParsingResults(TestSelection selection, Iterable<? extends TestSuite> testSuites) {
		this.testSuites = testSuites;
		this.testSelection = selection;
	}
	
	public final Iterable<? extends TestSuite> testSuites;
	public final TestSelection testSelection;
}
