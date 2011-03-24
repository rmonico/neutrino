package org.ita.neutrino.junitparser;

import org.ita.neutrino.abstracttestparser.AbstractTestParser;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.codeparser.Environment;

public class JUnitParser extends AbstractTestParser {

	private Environment environment;
	private JUnitTestBattery battery;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void parse() throws TestParserException {
		battery = new JUnitTestBattery(environment.getSelection());
		
		battery.setCodeElement(environment);
		
		doBatteryParse();
	}

	private void doBatteryParse() {
		BatteryParser batteryParser = new BatteryParser();
		
		batteryParser.setBattery(battery);
		
		batteryParser.parse();
	}

	@Override
	public JUnitTestBattery getBattery() {
		return battery;
	}

}
