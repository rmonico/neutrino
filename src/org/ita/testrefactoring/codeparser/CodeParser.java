package org.ita.testrefactoring.codeparser;

public interface CodeParser {
	void parse() throws ParserException;
	
	Environment getEnvironment();

	CodeSelection getSelection();
}
