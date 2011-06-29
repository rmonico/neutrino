package org.ita.neutrino.abstracttestparser;

import org.ita.neutrino.codeparser.MethodInvocationStatement;

public interface Assertion extends TestStatement {

	@Override
	public MethodInvocationStatement getCodeElement();

	public String getExplanation();
	
	public void setExplanation(String explanation);
}
