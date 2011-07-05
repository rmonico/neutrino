package org.ita.neutrino.junitgenericparser;

import java.util.List;

import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MethodInvocationStatement;
import org.ita.neutrino.codeparser.Statement;

public abstract class JUnitTestMethod implements TestMethod {

	protected abstract List<? extends JUnitTestStatement> instantiateStatementList();
	
	private JUnitTestSuite parent;
	private List<? extends JUnitTestStatement> statementList = instantiateStatementList();
	private Method element;

	protected JUnitTestMethod() {

	}

	protected abstract JUnitAction instantiateAction();
	
	@SuppressWarnings("unchecked")
	JUnitAction createAction(Statement statement) {
		JUnitAction action = instantiateAction();

		action.setParent(this);

		action.setCodeElement(statement);
		
		((List<JUnitAction>)statementList).add(action);
		
		return action;
	}

	protected abstract JUnitAssertion instantiateAssertion();
	
	@SuppressWarnings("unchecked")
	JUnitAssertion createAssertion(MethodInvocationStatement methodInvocation) {
		JUnitAssertion assertion = instantiateAssertion();

		assertion.setParent(this);
		
		assertion.setCodeElement(methodInvocation);
		
		((List<JUnitAssertion>)statementList).add(assertion);
		
		return assertion;
	}

	@Override
	public List<? extends JUnitTestStatement> getStatements() {
		return statementList;
	}

	@Override
	public JUnitTestSuite getParent() {
		return parent;
	}

	protected void setParent(JUnitTestSuite parent) {
		this.parent = parent;
	}

	@Override
	public Method getCodeElement() {
		return element;
	}

	void setCodeElement(Method element) {
		this.element = element;
	}

	@Override
	public String getName() {
		return element.getName();
	}

	@Override
	public String toString() {
		return getName();
	}
}
