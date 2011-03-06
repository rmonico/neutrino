package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.codeparser.Block;
import org.ita.testrefactoring.codeparser.Statement;

public class ASTBlock implements Block, ASTWrapper<org.eclipse.jdt.core.dom.Block> {
	
	private List<Statement> statementList = new ArrayList<Statement>();
	private org.eclipse.jdt.core.dom.Block astObject;
	private ASTMethod method;
	
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
	public ASTMethod getParent() {
		return method;
	}
	
	protected void setParentMethod(ASTMethod method) {
		this.method = method;
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
}
