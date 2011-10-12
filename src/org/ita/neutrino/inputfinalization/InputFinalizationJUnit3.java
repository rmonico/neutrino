package org.ita.neutrino.inputfinalization;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class InputFinalizationJUnit3 extends InputFinalizationAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit3Parser();
	}

}
