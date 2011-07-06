package org.ita.neutrino.astparser;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.ita.neutrino.astparser.ASTSourceFile.ASTContainer;
import org.ita.neutrino.codeparser.Method;

public class ASTMutableTypeHandler extends ASTTypeHandler {

	private ASTType handled;

	public ASTMutableTypeHandler(ASTType handled) {
		super(handled);

		this.handled = handled;
	}

	/**
	 * Deve criar um método usando os objetos do AST para que seja refletido no
	 * código.
	 * 
	 * @param dummyType
	 * @param newMethodName
	 * @return
	 */
	public Method createNewMethod(String newMethodName) {
		// TODO: Continuar aqui, pegar o código do projeto antigo e colocar aqui

		ASTContainer compilationUnitASTContainer = handled.getParent().getASTObject();

		AST ast = compilationUnitASTContainer.getCompilationUnit().getAST();

		MethodDeclaration newSetup = ast.newMethodDeclaration();

		newSetup.setName(ast.newSimpleName("setup"));

		newSetup.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));

		String methodSignature = newSetup.getName().toString();

		Method newSetupMethod = createMethod(methodSignature);

		ASTRewrite rewrite = compilationUnitASTContainer.getRewrite();

		// ListRewrite lrw = rewrite.getListRewrite(RefactoringUtils
		// .getPublicClassFrom(compilationUnitASTContainer.getCompilationUnit()),
		// TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
		ListRewrite lrw = rewrite.getListRewrite(handled.getASTObject(), TypeDeclaration.BODY_DECLARATIONS_PROPERTY);

		lrw.insertFirst(newSetup, null);

		NormalAnnotation beforeAnnotation = ast.newNormalAnnotation();

		String[] name = { "org", "junit", "Before" };

		beforeAnnotation.setTypeName(ast.newName(name));

		rewrite.getListRewrite(newSetup, MethodDeclaration.MODIFIERS2_PROPERTY).insertFirst(beforeAnnotation, null);
		
		return newSetupMethod;
	}

}
