package org.ita.neutrino.extractfinalizationmethod;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class ExtractFinalizationMethodJUnit3 extends ExtractFinalizationMethodAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		return new JUnit3Parser();
	}

}
