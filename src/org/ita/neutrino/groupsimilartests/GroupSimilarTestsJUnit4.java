package org.ita.neutrino.groupsimilartests;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit4parser.JUnit4Parser;

public class GroupSimilarTestsJUnit4 extends GroupSimilarTestsAction {
	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit4Parser();
	}
}
