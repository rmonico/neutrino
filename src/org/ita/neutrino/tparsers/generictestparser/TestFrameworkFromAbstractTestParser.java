package org.ita.neutrino.tparsers.generictestparser;

import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.tparsers.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.tparsers.abstracttestparser.TestParserException;

public class TestFrameworkFromAbstractTestParser extends TestFramework {	
	
	private final AbstractTestParser adapted;
	
	public TestFrameworkFromAbstractTestParser(AbstractTestParser adapted) {
		this.adapted = adapted;
	}
	
	@Override
	public TestFrameworkParsingResults parse(Environment environment) throws TestParserException {
		adapted.setEnvironment(environment);
		adapted.parse();
		
		return new TestFrameworkParsingResults(adapted.getBattery().getSelection(), adapted.getBattery().getSuiteList());
	}
}
