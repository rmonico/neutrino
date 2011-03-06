package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.abstracttestparser.TestBattery;
import org.ita.testrefactoring.abstracttestparser.TestSuite;
import org.ita.testrefactoring.codeparser.Type;

public class JUnitTestBattery extends TestBattery {

	private List<TestSuite> testSuiteList = new ArrayList<TestSuite>();

	JUnitTestBattery() {
	}

	@Override
	public List<TestSuite> getTestSuiteList() {
		return testSuiteList;
	}

	JUnitTestSuite createSuite(Type type) {
		JUnitTestSuite suite = new JUnitTestSuite();

		suite.setParent(this);
		
		suite.setCodeElement(type);

		return suite;
	}

}
