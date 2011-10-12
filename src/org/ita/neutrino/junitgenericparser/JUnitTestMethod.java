package org.ita.neutrino.junitgenericparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.MethodInvocationStatement;
import org.ita.neutrino.codeparser.MutableMethod;
import org.ita.neutrino.codeparser.Statement;

public abstract class JUnitTestMethod implements TestMethod {

	protected abstract List<? extends JUnitTestStatement> instantiateStatementList();

	private JUnitTestSuite parent;
	private List<? extends JUnitTestStatement> statementList = instantiateStatementList();
	private MutableMethod element;

	protected JUnitTestMethod() {

	}

	protected abstract JUnitAction instantiateAction();

	@SuppressWarnings("unchecked")
	JUnitAction createAction(Statement statement) {
		JUnitAction action = instantiateAction();

		action.setParent(this);

		action.setCodeElement(statement);

		((List<JUnitAction>) statementList).add(action);

		return action;
	}

	protected abstract JUnitAssertion instantiateAssertion();

	@SuppressWarnings("unchecked")
	JUnitAssertion createAssertion(MethodInvocationStatement methodInvocation) {
		JUnitAssertion assertion = instantiateAssertion();

		assertion.setParent(this);

		assertion.setCodeElement(methodInvocation);

		((List<JUnitAssertion>) statementList).add(assertion);

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
	public MutableMethod getCodeElement() {
		return element;
	}

	void setCodeElement(MutableMethod element) {
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

	@Override
	public void removeStatements(int index, int count) {
		getCodeElement().removeStatements(index, count);
	}

	@Override
	public void addStatements(List<TestStatement> testStatements, int index) {
		List<Statement> codeStatements = new ArrayList<Statement>();

		for (TestStatement codeStatement : testStatements) {
			codeStatements.add(codeStatement.getCodeElement());
		}

		getCodeElement().addStatements(codeStatements, index);
	}

	@Override
	public boolean hasAfterAnnotation() {
		for (Annotation item : element.getAnnotations()) {
			if (item.getQualifiedName().equals("org.junit.After")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isTestMethod() {
		return getParent().getTestMethodList().indexOf(this) > -1;
	}

}
