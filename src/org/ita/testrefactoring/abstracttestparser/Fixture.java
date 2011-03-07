package org.ita.testrefactoring.abstracttestparser;

public interface Fixture extends TestElement {

	TestSuite getParent();

	String getName();
}
