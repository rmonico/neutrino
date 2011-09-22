package org.ita.neutrino.groupsimilartests;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class GroupSimilarTestsJUnit3 extends GroupSimilarTestsAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit3Parser();
	}
}
