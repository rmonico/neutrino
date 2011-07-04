package org.ita.neutrino.addexplanation;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit4parser.JUnit4Parser;

public class AddExplanationJUnit4 extends AddExplanationAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		return new JUnit4Parser();
	}

}
