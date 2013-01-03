package org.ita.neutrino.tparsers.abstracttestparser;

import org.ita.neutrino.codeparser.Field;

public interface Fixture extends TestElement<Field> {

	TestSuite getParent();

	String getName();
}
