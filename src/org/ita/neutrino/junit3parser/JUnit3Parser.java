package org.ita.neutrino.junit3parser;

import org.ita.neutrino.codeparser.CodeSelection;
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

}
