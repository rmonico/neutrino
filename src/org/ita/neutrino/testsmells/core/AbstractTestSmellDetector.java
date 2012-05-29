package org.ita.neutrino.testsmells.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.abstracttestparser.Assertion;
import org.ita.neutrino.abstracttestparser.TestBattery;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.testsmells.smells.TestCodeSmell;
import org.ita.neutrino.testsmells.smells.TestCodeSmellLibrary;

import com.google.inject.Provider;

public abstract class AbstractTestSmellDetector {	
	
	protected final MarkerManager markerManager;
	protected final TestCodeSmellLibrary smellLibrary;
	protected final Provider<AbstractTestParser> testParserProvider;
	protected final DebugOutputSink debugOutputSink;

	public AbstractTestSmellDetector(
			MarkerManager markerManager,
			TestCodeSmellLibrary testCodeSmellLibrary,
			Provider<AbstractTestParser> testParserProvider,
			DebugOutputSink debugOutputSink) {
		this.markerManager = markerManager;
		this.smellLibrary = testCodeSmellLibrary;
		this.testParserProvider = testParserProvider;
		this.debugOutputSink = debugOutputSink;
	}
	
	protected void run(Environment environment, ICancelSignal cancelSignal) throws 
		SignalSetException, JavaModelException, CoreException {

		TestBattery testBattery = parseTestStructure(environment);
		debugOutputSink.println("[TCA] Parsed tests");
		cancelSignal.checkCancel();

		for (TestCodeSmell<TestBattery> smell : smellLibrary.getCodeSmellsAtLevel(TestBattery.class)) {
			smell.checkForPresence(testBattery, markerManager);
			cancelSignal.checkCancel();
		}
		
		for (TestSuite testSuite : testBattery.getSuiteList()) {
			debugOutputSink.println("[TCA] Suite: " + testSuite.getName());						
			markerManager.clearMarkers(testSuite);
			
			for (TestCodeSmell<TestSuite> smell : smellLibrary.getCodeSmellsAtLevel(TestSuite.class)) {
				smell.checkForPresence(testSuite, markerManager);
				cancelSignal.checkCancel();
			}
			
			for (TestMethod testMethod : testSuite.getTestMethodList()) {
				debugOutputSink.println("[TCA] Method: " + testMethod.getName());
				for (TestCodeSmell<TestMethod> smell : smellLibrary
						.getCodeSmellsAtLevel(TestMethod.class)) {
					smell.checkForPresence(testMethod, markerManager);
					cancelSignal.checkCancel();
				}
				
				for (TestStatement testStatement : testMethod.getStatements()) {
					if (testStatement.isAssertion()) {
						for (TestCodeSmell<Assertion> smell : smellLibrary.getCodeSmellsAtLevel(Assertion.class)) {
							smell.checkForPresence((Assertion) testStatement, markerManager);
							cancelSignal.checkCancel();
						}
					}
				}
			}

			cancelSignal.checkCancel();
		}

		return;
	}

	private TestBattery parseTestStructure(Environment environment) {
		AbstractTestParser testParser = testParserProvider.get();
		testParser.setEnvironment(environment);

		try {
			testParser.parse();
		} catch (TestParserException e) {
			throw new RuntimeException("Test parsing failed", e);
		}

		return testParser.getBattery();
	}
}