package org.ita.neutrino.junit4parser;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.MutableType;
import org.ita.neutrino.generictestparser.AbstractTestParserTestSuiteParser;
import org.ita.neutrino.generictestparser.TestSuiteParser;
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
				return clazz instanceof MutableType &&
					new BatteryParser().canParse((MutableType)clazz);
			}
		};
	}
}
