package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Constructor;
import org.ita.testrefactoring.metacode.Field;
import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.Package;

public class DummyType extends org.ita.testrefactoring.metacode.DummyType {

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
