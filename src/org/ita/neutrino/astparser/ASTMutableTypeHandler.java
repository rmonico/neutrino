package org.ita.neutrino.astparser;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
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
		
		AST ast = handled.getASTObject().getAST();
		
		MethodDeclaration newSetup = ast.newMethodDeclaration();

		newSetup.setName(ast.newSimpleName("setup"));
		
		newSetup.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));
		
		String methodSignature = newSetup.getName().toString();

		Method newSetupMethod = createMethod(methodSignature);
		
		return newSetupMethod;
	}


}
