package org.ita.neutrino.splitincrementaltests;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit4parser.JUnit4Parser;

public class SplitIncrementalTestsJUnit4 extends SplitIncrementalTestsAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit4Parser();
	}

}
