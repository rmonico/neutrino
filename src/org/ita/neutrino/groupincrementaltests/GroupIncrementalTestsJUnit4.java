package org.ita.neutrino.groupincrementaltests;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit4parser.JUnit4Parser;

public class GroupIncrementalTestsJUnit4 extends GroupIncrementalTestsAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit4Parser();
	}
}
