package org.ita.neutrino.junit3parser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.eclipseaction.NotImplementedYetException;
import org.ita.neutrino.junitgenericparser.JUnitTestStatement;

public class JUnitTestMethod extends org.ita.neutrino.junitgenericparser.JUnitTestMethod {

	@Override
	protected List<JUnitTestStatement> instantiateStatementList() {
		return new ArrayList<JUnitTestStatement>();
	}

	JUnitTestMethod() {
		super();
	}

	@Override
	protected JUnitAction instantiateAction() {
		return new JUnitAction();
	}

	protected JUnitAssertion instantiateAssertion() {
		return new JUnitAssertion();
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JUnitTestStatement> getStatements() {
		return (List<JUnitTestStatement>) super.getStatements();
	}

	@Override
	public JUnitTestSuite getParent() {
		return (JUnitTestSuite) super.getParent();
	}

	void setParent(JUnitTestSuite parent) {
		super.setParent(parent);
	}

	@Override
	public void addStatements(List<TestStatement> statements) {
		throw new NotImplementedYetException();
	}

}
