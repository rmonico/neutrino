package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.codeparser.Constructor;
import org.ita.testrefactoring.codeparser.Field;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.Package;
import org.ita.testrefactoring.codeparser.TypeKind;

/**
 * Representa um tipo que é uma classe, mas sem código fonte disponível.
 * 
 * @author Rafael Monico
 *
 */
public class DummyClass extends org.ita.testrefactoring.codeparser.DummyClass {

	private ASTTypeHandler handler = new ASTTypeHandler(this);

	@Override
	protected void setName(String name) {
		super.setName(name);
	}
	
	@Override
	protected void setPackage(Package pack) {
		super.setPackage(pack);
	}

	@Override
	public TypeKind getKind() {
		return TypeKind.CLASS;
	}

	@Override
	protected Field createField(String fieldName) {
		return handler.createField(fieldName);
	}

	@Override
	protected Constructor createConstructor(String constructorSignature) {
		return handler.createConstructor(constructorSignature);
	}

	@Override
	protected Method createMethod(String methodSignature) {
		return handler.createMethod(methodSignature);
	}

}
