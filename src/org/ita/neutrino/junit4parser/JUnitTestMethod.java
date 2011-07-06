package org.ita.neutrino.junit4parser;

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
	public void addStatements(List<TestStatement> testStatements) {
		throw new NotImplementedYetException();
		// TODO
//		List<Statement> codeStatements = new ArrayList<Statement>();
//		
//		for (TestStatement codeStatement : testStatements) {
//			codeStatements.add(codeStatement.getCodeElement());
//		}
//		
//		TestMethod newBeforeMethod = getCodeElement().createMethod(getNewBeforeTestsMethodName(), codeStatements);
		
	}

}
