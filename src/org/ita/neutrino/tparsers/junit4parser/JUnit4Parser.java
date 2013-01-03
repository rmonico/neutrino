package org.ita.neutrino.tparsers.junit4parser;

import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.tparsers.generictestparser.TestFramework;
import org.ita.neutrino.tparsers.generictestparser.TestFrameworkFromAbstractTestParser;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitGenericParser;

public class JUnit4Parser extends JUnitGenericParser {

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
