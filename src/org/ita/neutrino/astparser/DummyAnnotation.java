package org.ita.neutrino.astparser;

import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.Package;


public class DummyAnnotation extends org.ita.neutrino.codeparser.DummyAnnotation {

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
	public Method createNewMethod(String newMethodName) {
		return handler.createNewMethod(newMethodName);
	}
}
