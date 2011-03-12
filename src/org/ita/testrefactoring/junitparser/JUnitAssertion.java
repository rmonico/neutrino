package org.ita.testrefactoring.junitparser;

import org.ita.testrefactoring.abstracttestparser.Assertion;
import org.ita.testrefactoring.codeparser.MethodInvocationStatement;

public class JUnitAssertion extends JUnitTestStatement implements Assertion {

	private MethodInvocationStatement element;

	@Override
	public MethodInvocationStatement getCodeElement() {
		return element;
	}

	void setCodeElement(MethodInvocationStatement element) {
		this.element = element;
	}

	@Override
	public int getExplanationIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
