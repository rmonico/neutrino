package org.ita.neutrino.tparsers.generictestparser;

import java.util.Arrays;

import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.tparsers.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.tparsers.abstracttestparser.TestBattery;
import org.ita.neutrino.tparsers.abstracttestparser.TestParserException;

public class GenericTestParser extends AbstractTestParser {
	private final Iterable<TestFramework> implementations;
	private Environment environment;
	private TestBattery battery;
	
	public GenericTestParser(TestFramework... implementations) {
		this(Arrays.asList(implementations));
	}
	
	public GenericTestParser(Iterable<TestFramework> implementations) {
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
