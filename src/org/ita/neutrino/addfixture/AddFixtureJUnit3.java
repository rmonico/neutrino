package org.ita.neutrino.addfixture;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class AddFixtureJUnit3 extends AddFixtureAction {

	@Override
	protected AbstractTestParser instantiateParser() {
		// TODO Auto-generated method stub
		return new JUnit3Parser();
	}


}
