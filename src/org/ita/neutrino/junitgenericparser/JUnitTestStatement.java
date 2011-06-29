package org.ita.neutrino.junitgenericparser;

import org.ita.neutrino.abstracttestparser.TestStatement;

public interface JUnitTestStatement extends TestStatement {

	public void setParent(JUnitTestMethod parent);
	
	public JUnitTestMethod getParent();
}
