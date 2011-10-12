package org.ita.neutrino.junit4parser;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.generictestparser.AbstractTestParserTestSuiteParser;
import org.ita.neutrino.generictestparser.TestSuiteParser;
import org.ita.neutrino.junitgenericparser.BatteryParser.TestMethodKind;
import org.ita.neutrino.junitgenericparser.JUnitGenericParser;

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

	public TestSuiteParser asTestSuiteParser() {
		return new AbstractTestParserTestSuiteParser() {
			
			@Override
			protected AbstractTestParser getAbstractTestParser() {
				return JUnit4Parser.this;
			}
			
			@Override
			public boolean canParse(Class clazz) {
				BatteryParser batteryParser = createBatteryParser();
				
				for (Method method : clazz.getMethodList().values()) {
					if (batteryParser.getTestMethodKind(method) == TestMethodKind.TEST_METHOD) {
						return true;
					}
				}
				
				return false;
			}
		};
	}
}
