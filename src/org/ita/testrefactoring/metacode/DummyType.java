package org.ita.testrefactoring.metacode;

import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.ita.testrefactoring.astparser.ASTType;
import org.ita.testrefactoring.astparser.TypeKind;


/**
 * Representa um tipo de dados onde o código fonte não está disponível.
 * 
 * @author Rafael Monico
 * 
 */
public class DummyType extends ASTType {

	@Override
	public TypeKind getKind() {
		return TypeKind.UNKNOWN;
	}
	
	@Override
	public TypeDeclaration getASTObject() {
		return null;
	}
}
