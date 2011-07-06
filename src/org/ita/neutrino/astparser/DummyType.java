package org.ita.neutrino.astparser;

import org.ita.neutrino.codeparser.Constructor;
import org.ita.neutrino.codeparser.Field;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.Package;

public class DummyType extends org.ita.neutrino.codeparser.DummyType {

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

	@Override
	public Method createNewMethod(String newMethodName) {
		return handler.createNewMethod(newMethodName);
	}

}
