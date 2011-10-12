package org.ita.neutrino.junit3parser;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.codeparser.Class;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.generictestparser.AbstractTestParserTestSuiteParser;
import org.ita.neutrino.generictestparser.TestSuiteParser;
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

	public TestSuiteParser asTestSuiteParser() {
		return new AbstractTestParserTestSuiteParser() {
			
			@Override
			protected AbstractTestParser getAbstractTestParser() {
				return JUnit3Parser.this;
			}
			
			@Override
			public boolean canParse(Class clazz) {
				while (clazz != null) {
					if (clazz.getQualifiedName().equals("junit.framework.TestCase")) {
						return true;
					}
					clazz = clazz.getSuperClass();
				}
				
				return false;
			}
		};
	}
}
