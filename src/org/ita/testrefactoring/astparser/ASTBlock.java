package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.metacode.Block;
import org.ita.testrefactoring.metacode.Statement;

public class ASTBlock implements Block, ASTWrapper<org.eclipse.jdt.core.dom.Block> {
	
	private List<Statement> statementList = new ArrayList<Statement>();
	private org.eclipse.jdt.core.dom.Block astObject;
	private ASTConcreteMethod method;
	
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
	public ASTConcreteMethod getMethod() {
		return method;
	}
	
	protected void setParent(ASTConcreteMethod method) {
		this.method = method;
	}

	protected ASTVariableDeclarationStatement createVariableDeclaration(String variableName) {
		ASTVariableDeclarationStatement variableDeclaration = new ASTVariableDeclarationStatement();
		
		variableDeclaration.setParent(this);
		variableDeclaration.setVariableName(variableName);
		
		return variableDeclaration;
	}
}
