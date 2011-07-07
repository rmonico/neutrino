package org.ita.neutrino.extractinitializationmethod;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class ExtractInitializationMethodJUnit3 extends ExtractInitializationMethodAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		return new JUnit3Parser();
	}

}
