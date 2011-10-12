package org.ita.neutrino.inputinitialization;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class InputInitializationJUnit3 extends InputInitializationAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit3Parser();
	}

}
