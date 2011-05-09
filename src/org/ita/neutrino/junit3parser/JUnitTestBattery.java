package org.ita.neutrino.junit3parser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracttestparser.TestBattery;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.Type;

public class JUnitTestBattery extends TestBattery {

	private List<JUnitTestSuite> suiteList = new ArrayList<JUnitTestSuite>();
	private Environment environment;
	private JUnitSelection selection;

	JUnitTestBattery(CodeSelection codeSelection) {
		selection = new JUnitSelection(codeSelection);
	}

	@Override
	public List<JUnitTestSuite> getSuiteList() {
		return suiteList;
	}
	
	public JUnitTestSuite getSuiteByName(String suiteName) {
		if (suiteName == null) {
			return null;
		}
		
		for (JUnitTestSuite suite : suiteList) {
			if (suiteName.equals(suite.getName())) {
				return suite;
			}
		}
		
		return null;
	}

	JUnitTestSuite createSuite(Type type) {
		JUnitTestSuite suite = new JUnitTestSuite();

		suite.setParent(this);
		
		suite.setCodeElement(type);
		
		suiteList.add(suite);

		return suite;
	}

	@Override
	public Environment getCodeElement() {
		return environment;
	}
	
	void setCodeElement(Environment environment) {
		this.environment = environment;
	}

	@Override
	public JUnitSelection getSelection() {
		return selection;
	}

	@Override
	public void applyChanges() throws TestParserException {
		try {
			environment.applyChanges();
		} catch (ParserException e) {
			throw new TestParserException(e);
		}
	}

}
