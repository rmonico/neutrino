package org.ita.neutrino.junitgenericparser;

import org.ita.neutrino.abstracttestparser.Action;
import org.ita.neutrino.codeparser.Statement;

public abstract class JUnitAction implements JUnitTestStatement, Action {

	private Statement element;
	private JUnitTestMethod parent;
	private JUnitTestStatementHandler handler = new JUnitTestStatementHandler(this);

	protected JUnitAction() {
		super();
	}

	/**
	 * Usuários externos: método publicado por exigência da interface
	 * JUnitTestStatement. Utilizar a versão correspondente do parser
	 * específico! Não chamar esse método!
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
}
