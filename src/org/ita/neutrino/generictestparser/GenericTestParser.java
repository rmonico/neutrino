package org.ita.neutrino.generictestparser;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.abstracttestparser.TestBattery;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.codeparser.Environment;

public class GenericTestParser extends AbstractTestParser {
	private final Iterable<TestSuiteParser> implementations;
	private Environment environment;
	private TestBattery battery;
	
	public GenericTestParser(Iterable<TestSuiteParser> implementations) {
		this.implementations = implementations;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void parse() throws TestParserException {
		this.battery = new GenericTestBattery(this.environment, this.implementations);
		
	}

	@Override
	public TestBattery getBattery() {
		return this.battery;
	}
}
