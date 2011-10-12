package org.ita.neutrino.generictestparser;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.codeparser.Environment;

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
