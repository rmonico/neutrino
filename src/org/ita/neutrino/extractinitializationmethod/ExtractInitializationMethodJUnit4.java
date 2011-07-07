package org.ita.neutrino.extractinitializationmethod;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit4parser.JUnit4Parser;

public class ExtractInitializationMethodJUnit4 extends ExtractInitializationMethodAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		return new JUnit4Parser();
	}

}
