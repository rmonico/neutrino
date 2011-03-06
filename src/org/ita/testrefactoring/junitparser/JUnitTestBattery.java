package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.abstracttestparser.TestBattery;
import org.ita.testrefactoring.codeparser.Type;

public class JUnitTestBattery extends TestBattery {

	private List<JUnitTestSuite> suiteList = new ArrayList<JUnitTestSuite>();

	JUnitTestBattery() {
	}

	@Override
	public List<JUnitTestSuite> getSuiteList() {
		return suiteList;
	}

	JUnitTestSuite createSuite(Type type) {
		JUnitTestSuite suite = new JUnitTestSuite();

		suite.setParent(this);
		
		suite.setCodeElement(type);
		
		suiteList.add(suite);

		return suite;
	}

}
