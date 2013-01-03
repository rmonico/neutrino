package org.ita.neutrino.codeparser.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Block;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.codeparser.Invokable;
import org.ita.neutrino.codeparser.Statement;

public class ASTBlock extends AbstractCodeElement implements Block, ASTWrapper<org.eclipse.jdt.core.dom.Block> {

	private List<Statement> statementList = new ArrayList<Statement>();
	private org.eclipse.jdt.core.dom.Block astObject;

	@Override
	public List<Statement> getStatementList() {
		return statementList;
	}

	@Override
	public void setASTObject(org.eclipse.jdt.core.dom.Block astObject) {
		this.astObject = astObject;
	}

	@Override
	public org.eclipse.jdt.core.dom.Block getASTObject() {
		return astObject;
	}

	@Override
	public Invokable getParentInvokable() {

		CodeElement e = parent;

		// A segunda condição existe para impedir loops infinitos em função de
		// blocos mal-criados
		while (!(e instanceof Invokable) && (e != null))
			;

		assert false : "A Block should always be contained by a Invokable.";
		return (Invokable) e;
	}

	protected void setParent(CodeElement parent) {
		this.parent = parent;
	}

	protected ASTVariableDeclarationStatement createVariableDeclaration(String variableName) {
		ASTVariableDeclarationStatement variableDeclaration = new ASTVariableDeclarationStatement();

		variableDeclaration.setParent(this);
		variableDeclaration.setVariableName(variableName);

		return variableDeclaration;
	}

	public ASTGenericStatement createGenericStatement() {
		ASTGenericStatement genericStatement = new ASTGenericStatement();

		genericStatement.setParent(this);

		return genericStatement;
	}

	ASTMethodInvocationStatement createMethodInvocationStatement() {
		ASTMethodInvocationStatement methodInvocationStatement = new ASTMethodInvocationStatement();

		methodInvocationStatement.setParent(this);

		return methodInvocationStatement;
	}
}
