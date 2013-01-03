package org.ita.neutrino.testsmells.core;

import org.ita.neutrino.testsmells.smells.AssertionNotExplainedSmell;
import org.ita.neutrino.testsmells.smells.CompositeAssertionSmell;
import org.ita.neutrino.testsmells.smells.ConditionalFlowSmell;
import org.ita.neutrino.testsmells.smells.DuplicatedSetUpCodeSmell;
import org.ita.neutrino.testsmells.smells.DuplicatedTearDownCodeSmell;
import org.ita.neutrino.testsmells.smells.EagerTestSmell;
import org.ita.neutrino.testsmells.smells.SequentialAssertionsSmell;
import org.ita.neutrino.testsmells.smells.TestCodeSmell;
import org.ita.neutrino.tparsers.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.tparsers.generictestparser.GenericTestParser;
import org.ita.neutrino.tparsers.generictestparser.TestFramework;
import org.ita.neutrino.tparsers.junit3parser.JUnit3Parser;
import org.ita.neutrino.tparsers.junit4parser.JUnit4Parser;

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
	
	@Provides @Named("requiredNumberOfTestStatements")
	public int requiredNumberOfTestStatements_thresholdForEagerTest() {
		return 16;
	}
	
	@Provides @Named("maxNumberOfConsecutiveAssertions")
	public int maxNumberOfConsecutiveAssertions_thresholdForSequentialAssertions() {
		return 3;
	}	
	
	@Provides
	public AbstractTestParser testParser(Iterable<TestFramework> implementations) {
		return new GenericTestParser(implementations);
	}
	
	@Provides
	public Iterable<TestFramework> allTestFrameworks() {
		return ImmutableList.of(
				new JUnit4Parser().asTestSuiteParser(),
				new JUnit3Parser().asTestSuiteParser());
	}
	
	@Provides
	public Iterable<? extends TestCodeSmell<?>> allCodeSmells(
			AssertionNotExplainedSmell assertionNotExplained,
			DuplicatedSetUpCodeSmell duplicatedSetUp,
			DuplicatedTearDownCodeSmell duplicatedTearDown,
			EagerTestSmell eagerTest,
			ConditionalFlowSmell conditionalFlow,
			CompositeAssertionSmell compositeAssertion,
			SequentialAssertionsSmell sequentialAssertions) {
		return ImmutableList.of(assertionNotExplained,
				duplicatedSetUp,
				duplicatedTearDown,
				eagerTest,
				conditionalFlow,
				compositeAssertion,
				sequentialAssertions);
	}	
	
	@Provides
	public DebugOutputSink debugOutputSink() {
		return new DebugOutputSink() {			
			@Override
			public void println(Object o) {
				// Ignore and do not print anything
			}
		};
	}
}
