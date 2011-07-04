package org.ita.neutrino.addexplanation;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class AddExplanationJUnit3 extends AddExplanationAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		return new JUnit3Parser();
	}

}
