package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.abstracttestparser.TestBattery;
import org.ita.testrefactoring.abstracttestparser.TestSuite;

public class JUnitTestBattery extends TestBattery {

	private List<TestSuite> testSuiteList = new ArrayList<TestSuite>();

	JUnitTestBattery() {
	}

	@Override
	public List<TestSuite> getTestSuiteList() {
		return testSuiteList;
	}

}
