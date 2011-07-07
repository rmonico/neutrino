package org.ita.neutrino.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.ita.neutrino.astparser.ASTSourceFile.ASTContainer;
import org.ita.neutrino.codeparser.MutableMethod;

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
	public MutableMethod createNewMethod(String newMethodName) {
		ASTContainer compilationUnitASTContainer = handled.getParent().getASTObject();

		AST ast = compilationUnitASTContainer.getCompilationUnit().getAST();

		MethodDeclaration newSetup = ast.newMethodDeclaration();

		newSetup.setName(ast.newSimpleName("setup"));

		newSetup.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));

		Block block = ast.newBlock();

		newSetup.setBody(block);

		String methodSignature = newSetup.getName().toString();

		ASTMethod newSetupMethod = createMethod(methodSignature);
		
		newSetupMethod.setASTObject(newSetup);
		
		@SuppressWarnings("unchecked")
		List<Modifier> modifiers = newSetup.modifiers();
		
		modifiers.add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));

		ASTRewrite rewrite = compilationUnitASTContainer.getRewrite();

		ListRewrite lrw = rewrite.getListRewrite(handled.getASTObject(), TypeDeclaration.BODY_DECLARATIONS_PROPERTY);

		lrw.insertFirst(newSetup, null);

		return newSetupMethod;
	}

}
