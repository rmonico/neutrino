package org.ita.neutrino.astparser;

import org.ita.neutrino.codeparser.Constructor;
import org.ita.neutrino.codeparser.Field;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.Package;
import org.ita.neutrino.codeparser.TypeKind;

/**
 * Representa um tipo que é uma classe, mas sem código fonte disponível.
 * 
 * @author Rafael Monico
 *
 */
public class DummyClass extends org.ita.neutrino.codeparser.DummyClass {

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

	@Override
	public void removeTestMethods(int index, int count) {
		// TODO Auto-generated method stub
		
	}

}
