package org.ita.testrefactoring.abstracttestparser;

import org.ita.testrefactoring.codeparser.Field;

public interface Fixture extends TestElement, CodeElementWrapper<Field> {

	TestSuite getParent();

	String getName();
}
