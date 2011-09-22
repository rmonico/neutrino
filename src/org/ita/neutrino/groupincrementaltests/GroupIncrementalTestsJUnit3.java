package org.ita.neutrino.groupincrementaltests;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class GroupIncrementalTestsJUnit3 extends GroupIncrementalTestsAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit3Parser();
	}
}
