package org.ita.neutrino.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.VariableDeclarationStatement;
import org.ita.neutrino.debug.ConsoleVisitor;

public class ASTVariableDeclarationStatement extends ASTAbstractStatement<ASTNode> implements VariableDeclarationStatement {

	private VariableDeclarationFragment fragment;
	private Type variableType;
	private String variableName;
	private Expression initializationExpression;

	@Override
	public Type getVariableType() {
		return variableType;
	}

	protected void setVariableType(Type declarationType) {
		this.variableType = declarationType;
	}

	@Override
	public String getVariableName() {
		return variableName;
	}

	protected void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	@Override
	public Expression getInitialization() {
		return initializationExpression;
	}

	protected void setInitializationExpression(Expression initializationExpression) {
		this.initializationExpression = initializationExpression;
	}

	@Override
	public void removeDeclaration() {
		// TODO Auto-generated method stub	
		
		ASTNode node = getASTObject();
		ConsoleVisitor.showNodes(node);
		
		AST objAst = node.getAST();
		fragment = getFragment(node);
		ASTNode newNode = fragment.copySubtree(objAst, fragment);
		
		org.eclipse.jdt.core.dom.VariableDeclarationStatement statement = objAst.newVariableDeclarationStatement((VariableDeclarationFragment) newNode);

		setASTObject(statement);
		// setASTObject(fragment);

	}

	private VariableDeclarationFragment getFragment(ASTNode node) {
		QuickVisitor visitor = new QuickVisitor();
		List<ASTNode> nodes = visitor.quickVisit(node);
		if (nodes != null && nodes.size() > 0) {
			for (ASTNode i : nodes) {
				if (i instanceof VariableDeclarationFragment) {
					VariableDeclarationFragment temp = (VariableDeclarationFragment) i;
					ConsoleVisitor.showNodes(temp);
					return temp;
				}
				// else { i = null; }
			}
		}
		return null;
	}

}
