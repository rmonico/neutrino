package org.ita.neutrino.astparser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.Invokable;
import org.ita.neutrino.codeparser.Package;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.SourceFile;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.VariableDeclarationStatement;
import org.ita.neutrino.debug.ConsoleVisitor;
import org.ita.neutrino.eclipseaction.Activator;

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

		// testeNewClassInvocation();

		// org.eclipse.jdt.core.dom.VariableDeclarationStatement statement =
		// objAst.newVariableDeclarationStatement((VariableDeclarationFragment)
		// newNode);

		/*
		 * CompilationUnit cu = (CompilationUnit)
		 * node.getParent().getParent().getParent().getParent().getParent();
		 * ImportDeclaration id = objAst.newImportDeclaration();
		 * id.setName(objAst
		 * .newName(getSimpleNames("ita.bruno.teste.Guitarra")));
		 * id.setOnDemand(false); cu.imports().add(id);
		 * 
		 * id = objAst.newImportDeclaration();
		 * id.setName(objAst.newName(getSimpleNames
		 * ("ita.bruno.teste.Guitarra.eTipoGuitarra"))); id.setOnDemand(false);
		 * cu.imports().add(id);
		 */
		// org.eclipse.jdt.core.dom.VariableDeclarationFragment statement2 =
		// blkParent.getAST().newVariableDeclarationFragment();

		// setASTObject(fragment);
		// blkParent.statements().add(es);
		// setInitializationExpression(cie);
		// testFragment(node);
		// testeStatement(node);
		// testAssignement2(node);
		// testFragmentExpression(node);
		testFragmentExpression2(node);
	}

	ASTConstructorInvocationExpression cie;

	private void testFragmentExpression(ASTNode node) {
		// get AST block
		Block blkParent = (Block) node.getParent();

		VariableDeclarationFragment fragment = getFragment(node);

		// STATEMENT: MISSING = MISSING
		Assignment a = blkParent.getAST().newAssignment();
		a.setOperator(Assignment.Operator.ASSIGN);

		// STATEMENT: this.varName = MISSING
		FieldAccess faL = blkParent.getAST().newFieldAccess();
		faL.setExpression(blkParent.getAST().newThisExpression());
		faL.setName(blkParent.getAST().newSimpleName("varName"));
		a.setLeftHandSide(faL);

		// STATEMENT: this.varName = new MISSING()
		FieldAccess faR = blkParent.getAST().newFieldAccess();
		org.eclipse.jdt.core.dom.Expression exp = fragment.getInitializer();
		try {
			faR.setExpression(exp);
		} catch (Exception e) {
			String s = e.toString();
			// TODO: handle exception
		}
		a.setRightHandSide(faR);

		// ENCAPSULATE THE ASSIGNEMENT INSERTING A SEMICOLUMN
		// STATEMENT: this.varName = new MISSING();
		ExpressionStatement ess = blkParent.getAST().newExpressionStatement(a);

		setASTObject(ess);
	}

	private void testFragmentExpression2(ASTNode node) {
		// get AST block
		Block blkParent = (Block) node.getParent();

		VariableDeclarationFragment fragment = getFragment(node);

		// STATEMENT: MISSING = MISSING
		Assignment a = blkParent.getAST().newAssignment();
		a.setOperator(Assignment.Operator.ASSIGN);

		// ENCAPSULATE THE ASSIGNEMENT INSERTING A SEMICOLUMN
		// STATEMENT: this.varName = new MISSING();
		ExpressionStatement ess = blkParent.getAST().newExpressionStatement(fragment.getInitializer());

		setASTObject(ess);
	}

	private void testeTiposObjetos(ASTNode node) {
		AST objAst = node.getAST();
		BreakStatement bs = objAst.newBreakStatement();
		AssertStatement as = objAst.newAssertStatement();
		ContinueStatement cs = objAst.newContinueStatement();
		EmptyStatement es = objAst.newEmptyStatement();
	}

	private void testeStatement(ASTNode node) {
		AST objAst = node.getAST();
		fragment = getFragment(node);
		ASTNode newNode = fragment.copySubtree(objAst, fragment);
		org.eclipse.jdt.core.dom.VariableDeclarationStatement statement = objAst.newVariableDeclarationStatement((VariableDeclarationFragment) newNode);
		setASTObject(statement);
	}

	private void testFragment(ASTNode node) {
		VariableDeclarationFragment fragment = getFragment(node);
		setASTObject(fragment);
	}

	private VariableDeclarationFragment getFragment(ASTNode node) {
		QuickVisitor visitor = new QuickVisitor();
		List<ASTNode> nodes = visitor.quickVisit(node);

		if (nodes != null && nodes.size() > 0) {
			for (ASTNode i : nodes) {
				if (i instanceof VariableDeclarationFragment) {
					VariableDeclarationFragment temp = (VariableDeclarationFragment) i;
					/*
					 * ConsoleVisitor.showNodes(temp); CompilationUnit
					 * nCompilationUnit = (CompilationUnit)
					 * getASTObject().getParent
					 * ().getParent().getParent().getParent(); List<ASTNode>
					 * nodes2 = visitor.quickVisit(i); for (ASTNode j : nodes2)
					 * { if (j instanceof ClassInstanceCreation) {
					 * testTransformFragment((ClassInstanceCreation) j,
					 * nCompilationUnit); } }
					 */
					return temp;
				}

			}
		}
		return null;
	}

	private void testAssignement(ASTNode node) {
		Block blkParent = (Block) node.getParent();
		Assignment a = blkParent.getAST().newAssignment();
		a.setOperator(Assignment.Operator.ASSIGN);
		// blkParent.statements().add(blkParent.getAST().newExpressionStatement(a));

		FieldAccess fa = blkParent.getAST().newFieldAccess();
		fa.setExpression(blkParent.getAST().newThisExpression());
		fa.setName(blkParent.getAST().newSimpleName("g1"));
		a.setLeftHandSide(fa);

		fa = blkParent.getAST().newFieldAccess();
		// fa.setExpression((org.eclipse.jdt.core.dom.Expression) newNode);
		fa.setExpression(blkParent.getAST().newThisExpression());
		fa.setName(blkParent.getAST().newSimpleName("g1"));

		a.setRightHandSide(fa);

		ExpressionStatement ess = blkParent.getAST().newExpressionStatement(a);

		setASTObject(ess);

	}

	private void testAssignement2(ASTNode node) {
		// get AST block
		Block blkParent = (Block) node.getParent();

		// STATEMENT: MISSING = MISSING
		Assignment a = blkParent.getAST().newAssignment();
		a.setOperator(Assignment.Operator.ASSIGN);

		// STATEMENT: this.varName = MISSING
		FieldAccess fa = blkParent.getAST().newFieldAccess();
		fa.setExpression(blkParent.getAST().newThisExpression());
		fa.setName(blkParent.getAST().newSimpleName("varName"));
		a.setLeftHandSide(fa);

		// STATEMENT: new StringBuilder();
		ClassInstanceCreation cic = null;
		cic = blkParent.getAST().newClassInstanceCreation();
		// it does not work but many examples on net leed to it.
		cic.setName(blkParent.getAST().newSimpleName("StringBuilder"));

		// STATEMENT: this.varName = new MISSING()
		fa = blkParent.getAST().newFieldAccess();
		fa.setExpression(blkParent.getAST().newThisExpression());
		fa.setName(blkParent.getAST().newSimpleName(cic.toString()));
		a.setRightHandSide(fa);

		// ENCAPSULATE THE ASSIGNEMENT INSERTING A SEMICOLUMN
		// STATEMENT: this.varName = new MISSING();
		ExpressionStatement ess = blkParent.getAST().newExpressionStatement(a);

		setASTObject(ess);
	}

	private void testTransformFragment(ClassInstanceCreation astNode, CompilationUnit cu) {
		ASTParser parser = new ASTParser();
		parser.setActiveCompilationUnit(getActiveCompilationUnit());
		List<ICompilationUnit> compilationUnits = new ArrayList<ICompilationUnit>();
		compilationUnits.add(getActiveCompilationUnit());
		parser.setCompilationUnits(compilationUnits.toArray(new ICompilationUnit[0]));
		try {
			parser.parse();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ASTEnvironment enviroment = parser.getEnvironment();

		String constructorSignatureString = ASTEnvironment.getConstructorSignature(astNode);

		cie = enviroment.createConstructorInvocationExpression(constructorSignatureString);

		cie.setASTObject(astNode);

		// variableDeclaration.setInitializationExpression(cie);

	}

	private ICompilationUnit getActiveCompilationUnit() {
		IWorkbench workbench = Activator.getDefault().getWorkbench();

		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();

		IWorkbenchPage page = workbenchWindow.getActivePage();

		IEditorPart editorPart = page.getActiveEditor();

		if (editorPart == null) {
			// Nenhuma janela de edição ativa
			return null;
		}

		IEditorInput editorInput = editorPart.getEditorInput();

		ITypeRoot typeRoot = JavaUI.getEditorInputTypeRoot(editorInput);

		return (ICompilationUnit) typeRoot;
	}

	private void testeNewClassInvocation() {
		AST nVariableDeclarationStatement = getASTObject().getAST();
		AST nBlock = getASTObject().getParent().getAST();
		AST nMethodDeclaration = getASTObject().getParent().getParent().getAST();
		AST nTypeDeclaration = getASTObject().getParent().getParent().getParent().getAST();
		AST nCompilationUnit = getASTObject().getParent().getParent().getParent().getParent().getAST();

		instanciarClasse(nVariableDeclarationStatement);
		instanciarClasse(nBlock);
		instanciarClasse(nMethodDeclaration);
		instanciarClasse(nTypeDeclaration);
		instanciarClasse(nCompilationUnit);
	}

	private void instanciarClasse(AST ast) {
		ClassInstanceCreation cic = null;
		try {
			cic = ast.newClassInstanceCreation();
			cic.setName(ast.newSimpleName("StringBuilder"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		cic = null;

	}

	static private String[] getSimpleNames(String qualifiedName) {
		StringTokenizer st = new StringTokenizer(qualifiedName, ".");
		ArrayList list = new ArrayList();
		while (st.hasMoreTokens()) {
			String name = st.nextToken().trim();
			if (!name.equals("*"))
				list.add(name);
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

}
