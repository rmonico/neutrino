package org.ita.testrefactoring.abstracttestparser;

import org.ita.testrefactoring.codeparser.Environment;


public abstract class AbstractTestParser {
	
	public abstract void setEnvironment(Environment environment);
	
	public abstract TestSelection getSelection();
	
	public abstract void parse() throws TestParserException;
	
	public abstract TestBattery getBattery();
	
}
