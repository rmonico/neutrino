package org.ita.neutrino.extractfinalizationmethod;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit4parser.JUnit4Parser;

public class ExtractFinalizationMethodJUnit4 extends ExtractFinalizationMethodAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		return new JUnit4Parser();
	}

}
