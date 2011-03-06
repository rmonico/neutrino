package org.ita.testrefactoring.codeparser;

public abstract class AbstractCodeParser {
	public abstract void parse() throws ParserException;
	
	public abstract Environment getEnvironment();

	public abstract CodeSelection getSelection();
}
