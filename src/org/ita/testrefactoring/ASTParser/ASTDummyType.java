package org.ita.testrefactoring.ASTParser;

import org.eclipse.jdt.core.dom.TypeDeclaration;


/**
 * Representa um tipo de dados onde o código fonte não está disponível.
 * 
 * @author Rafael Monico
 * 
 */
public class ASTDummyType extends ASTType {

	@Override
	public TypeKind getKind() {
		return TypeKind.UNKNOWN;
	}
	
	@Override
	public TypeDeclaration getASTObject() {
		return null;
	}

}
