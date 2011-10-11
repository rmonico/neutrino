package org.ita.neutrino.testsmells.core;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit4parser.JUnit4Parser;
import org.ita.neutrino.testsmells.smells.AssertionNotExplainedSmell;
import org.ita.neutrino.testsmells.smells.TestCodeSmell;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class TestSmellDetectorGuiceModule extends AbstractModule {
	@Override
	protected void configure() {		
	}
	
	@Provides
	public AbstractTestParser testParser() {
		return new JUnit4Parser();
	}
	
	@Provides
	public Iterable<? extends TestCodeSmell> allCodeSmells(
			AssertionNotExplainedSmell assertionNotExplained) {
		return ImmutableList.of(assertionNotExplained);
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
