package org.ita.testrefactoring.abstracttestparser;

import org.ita.testrefactoring.codeparser.MethodInvocationStatement;

public interface Assertion extends TestStatement {

	@Override
	public MethodInvocationStatement getCodeElement();
}
