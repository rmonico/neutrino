package org.ita.neutrino.astparser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
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
		
		Block blkParent = (Block) node.getParent();
		AST objAst = node.getAST();
		fragment = getFragment(node);
		ASTNode newNode = fragment.copySubtree(objAst, fragment);
			
		//org.eclipse.jdt.core.dom.VariableDeclarationStatement statement = objAst.newVariableDeclarationStatement((VariableDeclarationFragment) newNode);
		org.eclipse.jdt.core.dom.VariableDeclarationStatement statement = objAst.newVariableDeclarationStatement((VariableDeclarationFragment) newNode);
			
		/*
		CompilationUnit cu = (CompilationUnit) node.getParent().getParent().getParent().getParent().getParent();
		ImportDeclaration id =  objAst.newImportDeclaration();
		id.setName(objAst.newName(getSimpleNames("ita.bruno.teste.Guitarra")));
		id.setOnDemand(false);
		cu.imports().add(id);
	
		id =  objAst.newImportDeclaration();
		id.setName(objAst.newName(getSimpleNames("ita.bruno.teste.Guitarra.eTipoGuitarra")));
		id.setOnDemand(false);
		cu.imports().add(id);
	*/
		
		Assignment a = objAst.newAssignment();
		a.setOperator(Assignment.Operator.ASSIGN);
		//blkParent.statements().add(objAst.newExpressionStatement(a)); 
				
		FieldAccess fa = blkParent.getAST().newFieldAccess();
		fa.setExpression(objAst.newThisExpression());
		fa.setName(objAst.newSimpleName("g1"));
		a.setLeftHandSide(fa);					
		a.setRightHandSide(objAst.newSimpleName(fragment.getInitializer().toString()));
		
		setASTObject(a);
		// setASTObject(fragment);

	}
	

	static private String[] getSimpleNames(String qualifiedName) {
		StringTokenizer st = new StringTokenizer(qualifiedName,".");
		ArrayList list = new ArrayList();
		while (st.hasMoreTokens()) {
			String name = st.nextToken().trim();
			if (!name.equals("*"))
				list.add(name);
		}
		return (String[]) list.toArray(new String[list.size()]);
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
