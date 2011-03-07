package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.codeparser.Package;


public class DummyAnnotation extends org.ita.testrefactoring.codeparser.DummyAnnotation {

	@Override
	protected void setName(String name) {
		super.setName(name);
	}
	
	@Override
	protected void setPackage(Package pack) {
		super.setPackage(pack);
	}
}
