package org.ita.neutrino.generictestparser;

import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.abstracttestparser.TestSelection;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.Environment;

public interface TestSuiteParser {
	boolean canParse(Class clazz);
	
	TestSelection getSelection();
	
	TestSuite parse(Environment environment, Class clazz) throws TestParserException;
}
