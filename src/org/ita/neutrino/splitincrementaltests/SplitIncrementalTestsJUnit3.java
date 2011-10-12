package org.ita.neutrino.splitincrementaltests;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class SplitIncrementalTestsJUnit3 extends SplitIncrementalTestsAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit3Parser();
	}

}
