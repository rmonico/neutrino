package org.ita.neutrino.inputinitialization;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit4parser.JUnit4Parser;

public class InputInitializationJUnit4 extends InputInitializationAction{

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit4Parser();
	}

}
