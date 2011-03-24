package org.ita.neutrino.abstracttestparser;

import org.ita.neutrino.codeparser.Field;

public interface Fixture extends TestElement, CodeElementWrapper<Field> {

	TestSuite getParent();

	String getName();
}
