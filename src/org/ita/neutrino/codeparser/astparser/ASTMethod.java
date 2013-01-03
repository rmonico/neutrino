package org.ita.neutrino.codeparser.astparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.codeparser.Argument;
import org.ita.neutrino.codeparser.CheckedExceptionClass;
import org.ita.neutrino.codeparser.MutableMethod;
import org.ita.neutrino.codeparser.Statement;
import org.ita.neutrino.codeparser.Type;

public class ASTMethod extends AbstractCodeElement implements MutableMethod, ASTWrapper<MethodDeclaration> {

	private ASTInnerElementAccessModifier accessModifier = new ASTInnerElementAccessModifier();
	private String name;
	private ASTMethodDeclarationNonAccessModifier nonAccessModifier = new ASTMethodDeclarationNonAccessModifier();
	private MethodDeclaration astObject;
	private ASTBlock body;
	private List<Annotation> annotationList = new ArrayList<Annotation>();

	ASTBlock createBlock() {
		ASTBlock methodBlock = new ASTBlock();

		methodBlock.setParent(this);

		return methodBlock;
	}

	@Override
	public ASTInnerElementAccessModifier getAccessModifier() {
		return accessModifier;
	}

	void setAccessModifier(ASTInnerElementAccessModifier accessModifier) {
		this.accessModifier = accessModifier;
	}

	void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	void setParentType(Type parent) {
		this.parent = parent;
	}

	public Type getParent() {
		return (Type) super.getParent();
	}

	@Override
	public List<Annotation> getAnnotations() {
		return annotationList;
	}

	@Override
	public ASTMethodDeclarationNonAccessModifier getNonAccessModifier() {
		return nonAccessModifier;
	}

	void setNonAccessModifier(ASTMethodDeclarationNonAccessModifier nonAccessModifier) {
		this.nonAccessModifier = nonAccessModifier;
	}

	@Override
	public Type getReturnType() {
		return null;
	}

	@Override
	public List<Argument> getArgumentList() {
		return null;
	}

	@Override
	public List<CheckedExceptionClass> getThrownExceptions() {
		return null;
	}

	@Override
	public void setASTObject(MethodDeclaration astObject) {
		this.astObject = astObject;
	}

	@Override
	public MethodDeclaration getASTObject() {
		return astObject;
	}

	@Override
	public ASTBlock getBody() {
		if (nonAccessModifier.isAbstract()) {
			return null;
		}

		if (body == null) {
			body = createBlock();
		}

		return body;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Mutable method.
	 */
	@Override
	public void addAnnotation(Type annotation) {
		AST ast = astObject.getAST();

		ASTRewrite rewrite = ((ASTSourceFile) getParent().getParent()).getASTObject().getRewrite();

		NormalAnnotation astAnnotation = ast.newNormalAnnotation();

		astAnnotation.setTypeName(ast.newName(annotation.getQualifiedName()));

		rewrite.getListRewrite(astObject, MethodDeclaration.MODIFIERS2_PROPERTY).insertFirst(astAnnotation, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addStatements(List<Statement> codeStatements, int index) {
		Block block = getASTObject().getBody();

		AST ast = astObject.getAST();

		ASTRewrite rewrite = ((ASTType) getParent()).getParent().getASTObject().getRewrite();

		ListRewrite lrw = rewrite.getListRewrite(block, Block.STATEMENTS_PROPERTY);

		int originalListSize = lrw.getOriginalList().size();

		for (int i = 0; i < codeStatements.size(); i++) {
			ASTAbstractStatement<ASTNode> astStatement = (ASTAbstractStatement<ASTNode>) codeStatements.get(i);

			ASTNode astNode = astStatement.getASTObject();

			ASTNode copyOfAstNode = ASTNode.copySubtree(ast, astNode);

			// block.statements().add(copyOfAstNode);

			if (index == -1) {
				lrw.insertAt(copyOfAstNode, originalListSize + i, null);
			} else {
				lrw.insertAt(copyOfAstNode, index + i, null);
			}
		}
	}

	@Override
	public void removeStatements(int index, int count) {
		@SuppressWarnings("unchecked")
		List<ASTNode> statements = getASTObject().getBody().statements();

		ASTRewrite rewrite = ((ASTType) getParent()).getParent().getASTObject().getRewrite();

		ListRewrite listRewrite = rewrite.getListRewrite(getASTObject().getBody(), Block.STATEMENTS_PROPERTY);

		for (int i = index; i < index + count; i++) {
			listRewrite.remove(statements.get(i), null);
		}
	}

	public void createNewAssertStatement(ExpressionStatement eStatement, List<MethodInvocation> methods) {
		AST ast = getASTObject().getAST();
		ASTRewrite rewrite = ((ASTType) getParent()).getParent().getASTObject().getRewrite();
		ListRewrite listRewrite = rewrite.getListRewrite(getASTObject().getBody(), Block.STATEMENTS_PROPERTY);

		@SuppressWarnings("unchecked")
		List<ASTNode> statements = getASTObject().getBody().statements();

		for (int i = 0; i < statements.size(); i++) {

			if (statements.get(i) instanceof ExpressionStatement && ((ExpressionStatement) statements.get(i)) == eStatement) {
				ASTNode lastNode = statements.get(i);
				for (MethodInvocation item : methods) {
					ExpressionStatement es = ast.newExpressionStatement(item);

					// REWRITE
					listRewrite.insertAfter(es, lastNode, null);
					lastNode = es;
				}
				listRewrite.remove(statements.get(i), null);
				break;
			}
		}

	}
	
	public void setAbstract() {
		AST ast = getASTObject().getAST();
				
		ASTMethodDeclarationNonAccessModifier nonAcessModifier = new ASTMethodDeclarationNonAccessModifier();
		nonAcessModifier.setAbstract(true);
		setNonAccessModifier(nonAcessModifier); 
		
		MethodDeclaration newSetup = ast.newMethodDeclaration();

		newSetup.setName(ast.newSimpleName(getName()));
		newSetup.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));
		newSetup.setBody(null);

		String methodSignature = newSetup.getName().toString();

		ASTTypeHandler typeHandler = new ASTTypeHandler(getParent());
		/* ASTMethod newSetupMethod = new ASTMethod();

		newSetupMethod.setName(methodSignature);
		newSetupMethod.setParentType(getParent());

		getParent().getMethodList().put(methodSignature, newSetupMethod); */
		
		ASTMethod newSetupMethod = typeHandler.createMethod(methodSignature);
		newSetupMethod.setASTObject(newSetup);

		@SuppressWarnings("unchecked")
		List<Modifier> modifiers = newSetup.modifiers();
		modifiers.add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
		modifiers.add(ast.newModifier(Modifier.ModifierKeyword.ABSTRACT_KEYWORD));

		ASTRewrite rewrite = ((ASTType) getParent()).getParent().getASTObject().getRewrite();
		
		ListRewrite lrw = rewrite.getListRewrite(getASTObject().getParent(), TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
		lrw.insertAfter(newSetup, (ASTNode) getASTObject(), null);
		lrw.remove((ASTNode) getASTObject(), null);
	}
	
	public boolean isAbstract() {
		return getNonAccessModifier().isAbstract();
	}
}
