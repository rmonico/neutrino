package org.ita.neutrino.decomposeassertion;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit4parser.JUnit4Parser;

public class DecomposeAssertionJUnit4 extends DecomposeAssertionAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit4Parser();
	}

}
