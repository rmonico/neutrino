package org.ita.neutrino.abstracttestparser;

import org.ita.neutrino.codeparser.Environment;


public abstract class AbstractTestParser {
	
	public abstract void setEnvironment(Environment environment);
	
	public abstract void parse() throws TestParserException;
	
	public abstract TestBattery getBattery();
	
}
