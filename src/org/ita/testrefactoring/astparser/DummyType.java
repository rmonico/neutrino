package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.codeparser.Constructor;
import org.ita.testrefactoring.codeparser.Field;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.Package;

public class DummyType extends org.ita.testrefactoring.codeparser.DummyType {

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
