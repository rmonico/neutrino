package org.ita.neutrino.junitgenericparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracttestparser.Action;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.astparser.ASTVariableDeclarationStatement;
import org.ita.neutrino.codeparser.Statement;
import org.ita.neutrino.codeparser.VariableDeclarationStatement;

public abstract class JUnitAction implements JUnitTestStatement, Action {

	private Statement element;
	private JUnitTestMethod parent;
	private JUnitTestStatementHandler handler = new JUnitTestStatementHandler(this);

	protected JUnitAction() {
		super();
	}

	/**
	 * Usuários externos: método publicado por exigência da interface JUnitTestStatement. Utilizar a versão correspondente do parser específico! Não chamar esse método!
	 */
	@Override
	public void setParent(JUnitTestMethod parent) {
		this.parent = parent;
	}

	@Override
	public JUnitTestMethod getParent() {
		return parent;
	}

	@Override
	public Statement getCodeElement() {
		return element;
	}

	void setCodeElement(Statement element) {
		this.element = element;
	}

	@Override
	public String toString() {
		return getCodeElement().toString();
	}

	@Override
	public boolean equals(Object obj) {
		return handler.equals(obj);
	}
	
	@Override
	public TestStatement getStatement() {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	@Override
	public boolean isVariableDeclarationStatement() {
		Statement st = getCodeElement();
		return (st instanceof VariableDeclarationStatement);
	}

	public void transformInExpression() {
		transformInExpression(null);
	}

	public void transformInExpression(Action baseAction) {
		String varName = null;
		if (baseAction != null) {
			if (!baseAction.isVariableDeclarationStatement()) {
				throw new IllegalAccessError("baseAction must be a variable declaration statement!");
			} else {
				varName = ((VariableDeclarationStatement) baseAction.getCodeElement()).getVariableName();
			}
		}

		if (!getCodeElement().isVariableDeclaration()) {
			throw new IllegalAccessError("only variable declaration statement can be transformed!");
		}

		VariableDeclarationStatement var = (VariableDeclarationStatement) getCodeElement();
		TestMethod tm = (TestMethod) getParent();

		int statementIndex = tm.getStatements().indexOf(this);

		List<TestStatement> newStatement = new ArrayList<TestStatement>();
		if (var.getInitialization() != null) {
			newStatement.add(getTestStatementWithNoDeclaration(tm.getStatements().get(statementIndex), varName));
		}

		tm.removeStatements(statementIndex, 1);
		tm.addStatements(newStatement, statementIndex);
	}

	private TestStatement getTestStatementWithNoDeclaration(TestStatement from, String variableName) {
		ASTVariableDeclarationStatement element = (ASTVariableDeclarationStatement) from.getCodeElement();
		if (variableName == null) {
			element.transformInExpression();
		} else {
			element.transformInExpression(variableName);
		}
		return from;
	}
		
	@Override
	public boolean isAssertion() {
		return false;
	}
}
