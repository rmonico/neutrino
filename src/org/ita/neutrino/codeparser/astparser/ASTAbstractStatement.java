package org.ita.neutrino.codeparser.astparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.ASTNode;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Block;
import org.ita.neutrino.codeparser.Statement;
import org.ita.neutrino.codeparser.VariableDeclarationStatement;
import org.ita.neutrino.tparsers.abstracttestparser.Action;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;

class ASTAbstractStatement<T extends ASTNode> extends AbstractCodeElement implements Statement, ASTWrapper<T> {

	private T astObject;
	
	@Override
	public ASTBlock getParent() {
		return (ASTBlock) super.getParent();
	}
	
	protected void setParent(Block block) {
		parent = block;
	}

	@Override
	public void setASTObject(T astObject) {
		this.astObject = astObject;
		
	}

	@Override
	public T getASTObject() {
		return astObject;
	}

	@Override
	public String toString() {
		return getASTObject().toString();
	}

	public boolean isVariableDeclaration() {
		return (this instanceof VariableDeclarationStatement);
	}

	public void transformInExpression() {
		transformInExpression(null);
	}

	public void transformInExpression(Action baseAction) {
		String varName = null;
		if (baseAction != null && !baseAction.isVariableDeclarationStatement()) {
			throw new IllegalAccessError("baseAction must be a variable declaration statement!");
		} else {
			varName = ((VariableDeclarationStatement) baseAction.getCodeElement()).getVariableName();
		}

		if (!isVariableDeclaration()) {
			throw new IllegalAccessError("only variable declaration statement can be transformed!");
		}
		TestMethod tm = (TestMethod) getParent();
		
		VariableDeclarationStatement var = (VariableDeclarationStatement) this;

		int statementIndex = tm.getStatements().indexOf(var);

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
	public boolean similarDeclaration(Action action) {
		if (action.isVariableDeclarationStatement() && isVariableDeclaration()) {
			VariableDeclarationStatement var = (VariableDeclarationStatement) this;
			VariableDeclarationStatement varComparer = (VariableDeclarationStatement) action.getCodeElement();
			return (var.getVariableType().equals(varComparer.getVariableType()) && var.getVariableName().equals(varComparer.getVariableName()));
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ASTWrapper)) {
			return false;
		}
		
		ASTMatcher astMatcher = new ASTMatcher();
		return this.getASTObject().subtreeMatch(astMatcher, ((ASTWrapper<?>)obj).getASTObject());
	}
	
	@Override
	public boolean isBranchStatement() {
		return this.getASTObject() != null && ( 
			this.getASTObject().getNodeType() == ASTNode.IF_STATEMENT ||
			this.getASTObject().getNodeType() == ASTNode.SWITCH_STATEMENT);
	}
}
