package org.ita.neutrino.tparsers.abstracttestparser;

import org.ita.neutrino.codeparser.MethodInvocationStatement;

public interface Assertion extends TestStatement {

	@Override
	public MethodInvocationStatement getCodeElement();

	public String getExplanation();
	
	public void setExplanation(String explanation);
	
	public boolean hasMultipleChecks();
	
	public void decomposeAssertion();
	
}
