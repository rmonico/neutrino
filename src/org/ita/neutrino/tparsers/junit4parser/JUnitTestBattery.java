package org.ita.neutrino.tparsers.junit4parser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.codeparser.CodeSelection;

public class JUnitTestBattery extends org.ita.neutrino.tparsers.junitgenericparser.JUnitTestBattery {

	@Override
	protected JUnitSelection instantiateSelection(CodeSelection codeSelection) {
		return new JUnitSelection(codeSelection);
	}
	
	@Override
	protected List<JUnitTestSuite> instantiateSuiteList() {
		return new ArrayList<JUnitTestSuite>();
	}

	JUnitTestBattery(CodeSelection codeSelection) {
		super(codeSelection);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JUnitTestSuite> getSuiteList() {
		return (List<JUnitTestSuite>) super.getSuiteList();
	}
	
	public JUnitTestSuite getSuiteByName(String suiteName) {
		return (JUnitTestSuite) super.getSuiteByName(suiteName);
	}

	@Override
	protected JUnitTestSuite instantiateSuite() {
		return new JUnitTestSuite();
	}

	@Override
	public JUnitSelection getSelection() {
		return (JUnitSelection) super.getSelection();
	}
}
