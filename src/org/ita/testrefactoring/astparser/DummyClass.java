package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Constructor;
import org.ita.testrefactoring.metacode.Field;
import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.Package;
import org.ita.testrefactoring.metacode.TypeKind;

/**
 * Representa um tipo que é uma classe, mas sem código fonte disponível.
 * 
 * @author Rafael Monico
 *
 */
public class DummyClass extends org.ita.testrefactoring.metacode.DummyClass {

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
