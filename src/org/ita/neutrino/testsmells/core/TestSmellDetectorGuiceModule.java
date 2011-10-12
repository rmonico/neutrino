package org.ita.neutrino.testsmells.core;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.generictestparser.GenericTestParser;
import org.ita.neutrino.generictestparser.TestSuiteParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;
import org.ita.neutrino.junit4parser.JUnit4Parser;
import org.ita.neutrino.testsmells.smells.AssertionNotExplainedSmell;
import org.ita.neutrino.testsmells.smells.DuplicatedSetUpCodeSmell;
import org.ita.neutrino.testsmells.smells.DuplicatedTearDownCodeSmell;
import org.ita.neutrino.testsmells.smells.DuplicatedTearDownCodeSmell.DuplicatedTearDownCodeQuickFix;
import org.ita.neutrino.testsmells.smells.TestCodeSmell;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class TestSmellDetectorGuiceModule extends AbstractModule {
	@Override
	protected void configure() {		
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
			DuplicatedTearDownCodeSmell duplicatedTearDown) {
		return ImmutableList.of(assertionNotExplained,
				duplicatedSetUp,
				duplicatedTearDown);
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
