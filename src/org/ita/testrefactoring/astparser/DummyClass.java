package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Package;

/**
 * Representa um tipo que é uma classe, mas sem código fonte disponível.
 * 
 * @author Rafael Monico
 *
 */
public class DummyClass extends org.ita.testrefactoring.metacode.DummyClass {

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
}
