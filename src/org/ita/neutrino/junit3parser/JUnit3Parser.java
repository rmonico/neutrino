package org.ita.neutrino.junit3parser;

import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.generictestparser.TestFramework;
import org.ita.neutrino.generictestparser.TestFrameworkFromAbstractTestParser;
import org.ita.neutrino.junitgenericparser.JUnitGenericParser;

public class JUnit3Parser extends JUnitGenericParser {

	@Override
	protected JUnitTestBattery createTestBattery(CodeSelection selection) {
		return new JUnitTestBattery(selection); 
	}

	@Override
	protected BatteryParser createBatteryParser() {
		return new BatteryParser();
	}

	@Override
	public JUnitTestBattery getBattery() {
		return (JUnitTestBattery) super.getBattery();
	}

	public TestFramework asTestSuiteParser() {
		return new TestFrameworkFromAbstractTestParser(this);
	}
}
