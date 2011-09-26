package org.ita.neutrino.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.VariableDeclarationStatement;

public class ASTVariableDeclarationStatement extends ASTAbstractStatement<ASTNode> implements VariableDeclarationStatement {

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
	public void transformInExpression() {
		transformInExpression(this.variableName);
	}

	@Override
	public void transformInExpression(String variableName) {
		ASTNode node = getASTObject();
		// ConsoleVisitor.showNodes(node);

		AST ast = node.getAST();

		Assignment assignment = ast.newAssignment();
		assignment.setOperator(Assignment.Operator.ASSIGN);

		//FieldAccess fa = ast.newFieldAccess();
		//fa.setExpression(ast.newThisExpression());
		SimpleName variable = ast.newSimpleName(variableName);
		//fa.setName(variable);
		assignment.setLeftHandSide(variable);

		org.eclipse.jdt.core.dom.Expression initializationExpression = getInitializationExpression();
		ExpressionStatement expression = null;

		if (initializationExpression != null) {
			org.eclipse.jdt.core.dom.Expression copyOfinitializationExpression = (org.eclipse.jdt.core.dom.Expression) ASTNode.copySubtree(ast, initializationExpression);
			assignment.setRightHandSide(copyOfinitializationExpression);
			expression = ast.newExpressionStatement(assignment);
		}
		setASTObject(expression);
	}

	private org.eclipse.jdt.core.dom.Expression getInitializationExpression() {
		QuickVisitor quickVisitor = new QuickVisitor();

		List<ASTNode> nodes = quickVisitor.quickVisit(getASTObject());

		VariableDeclarationFragment variableDeclarationFragment = (VariableDeclarationFragment) nodes.get(1);

		List<ASTNode> variableDeclarationNodes = quickVisitor.quickVisit(variableDeclarationFragment);

		org.eclipse.jdt.core.dom.Expression initializationExpression = null;

		if (variableDeclarationNodes.size() > 1) {
			initializationExpression = (org.eclipse.jdt.core.dom.Expression) variableDeclarationNodes.get(1);
		}
		return initializationExpression;
	}
}
