package org.ita.neutrino.tparsers.junitgenericparser;

import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;

public interface JUnitTestStatement extends TestStatement {

	public void setParent(JUnitTestMethod parent);
	
	public JUnitTestMethod getParent();
}
