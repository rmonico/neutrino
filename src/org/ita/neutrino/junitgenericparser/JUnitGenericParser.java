package org.ita.neutrino.junitgenericparser;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.Environment;

public abstract class JUnitGenericParser extends AbstractTestParser {

	private Environment environment;
	private JUnitTestBattery battery;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	protected abstract JUnitTestBattery createTestBattery(CodeSelection selection);

	@Override
	public void parse() throws TestParserException {
		battery = createTestBattery(environment.getSelection());
		
		battery.setCodeElement(environment);
		
		doBatteryParse();
	}

	protected abstract BatteryParser createBatteryParser();
	
	private void doBatteryParse() {
		BatteryParser batteryParser = createBatteryParser();
		
		batteryParser.setBattery(battery);
		
		batteryParser.parse();
	}

	@Override
	public JUnitTestBattery getBattery() {
		return battery;
	}

}
