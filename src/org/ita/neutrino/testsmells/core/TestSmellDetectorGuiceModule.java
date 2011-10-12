package org.ita.neutrino.testsmells.core;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.generictestparser.GenericTestParser;
import org.ita.neutrino.generictestparser.TestSuiteParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;
import org.ita.neutrino.junit4parser.JUnit4Parser;
import org.ita.neutrino.testsmells.smells.AssertionNotExplainedSmell;
import org.ita.neutrino.testsmells.smells.CompositeAssertionSmell;
import org.ita.neutrino.testsmells.smells.ConditionalFlowSmell;
import org.ita.neutrino.testsmells.smells.DuplicatedSetUpCodeSmell;
import org.ita.neutrino.testsmells.smells.DuplicatedTearDownCodeSmell;
import org.ita.neutrino.testsmells.smells.EagerTestSmell;
import org.ita.neutrino.testsmells.smells.TestCodeSmell;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class TestSmellDetectorGuiceModule extends AbstractModule {
	@Override
	protected void configure() {
	}
	
	@Provides @Named("maxVerificationsPerTest")
	public int maxVerificationsPerTest_thresholdForEagerTest() {
		return 2;
	}
	
	@Provides
	public AbstractTestParser testParser(Iterable<TestSuiteParser> implementations) {
		return new GenericTestParser(implementations);
	}
	
	@Provides
	public Iterable<TestSuiteParser> allTestFrameworks() {
		return ImmutableList.of(
				new JUnit4Parser().asTestSuiteParser(),
				new JUnit3Parser().asTestSuiteParser());
	}
	
	@Provides
	public Iterable<? extends TestCodeSmell> allCodeSmells(
			AssertionNotExplainedSmell assertionNotExplained,
			DuplicatedSetUpCodeSmell duplicatedSetUp,
			DuplicatedTearDownCodeSmell duplicatedTearDown,
			EagerTestSmell eagerTest,
			ConditionalFlowSmell conditionalFlow,
			CompositeAssertionSmell compositeAssertion) {
		return ImmutableList.of(assertionNotExplained,
				duplicatedSetUp,
				duplicatedTearDown,
				eagerTest,
				conditionalFlow,
				compositeAssertion);
	}	
	
	@Provides
	public DebugOutputSink debugOutputSink() {
		return new DebugOutputSink() {			
			@Override
			public void println(Object o) {
				// Ignore and do not print anything
				System.out.println(o);
			}
		};
	}
}
